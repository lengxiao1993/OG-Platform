/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.position.db;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.time.InstantProvider;
import javax.time.TimeSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.opengamma.engine.position.Portfolio;
import com.opengamma.engine.position.PortfolioImpl;
import com.opengamma.engine.position.PortfolioNode;
import com.opengamma.engine.position.PortfolioNodeImpl;
import com.opengamma.engine.position.Position;
import com.opengamma.engine.position.PositionMaster;
import com.opengamma.engine.security.Security;
import com.opengamma.id.DomainSpecificIdentifier;
import com.opengamma.id.DomainSpecificIdentifiers;
import com.opengamma.util.ArgumentChecker;

/**
 * A Hibernate database backed implementation of a PositionMaster. 
 * 
 * @author Andrew Griffin
 */
public class HibernatePositionMaster implements PositionMaster, InitializingBean {

  private static final Logger s_logger = LoggerFactory.getLogger(HibernatePositionMaster.class);
  
  private HibernateTemplate _hibernateTemplate;
  
  public HibernatePositionMaster () {
  }
  
  public void setSessionFactory (final SessionFactory sessionFactory) {
    ArgumentChecker.checkNotNull(sessionFactory, "sessionFactory");
    _hibernateTemplate = new HibernateTemplate (sessionFactory);
  }
  
  protected HibernateTemplate getHibernateTemplate () {
    return _hibernateTemplate;
  }
  
  @Override
  public void afterPropertiesSet () throws Exception {
    if (getHibernateTemplate () == null) {
      throw new IllegalStateException ("sessionFactory not set");
    }
  }
  
  private static class PositionImpl implements Position {
    private final DomainSpecificIdentifier _identityKey;
    private final BigDecimal _quantity;
    private final DomainSpecificIdentifiers _securityKey;
    private PositionImpl (final String identifier, final BigDecimal quantity, final DomainSpecificIdentifiers securityKey) {
      _identityKey = new DomainSpecificIdentifier (POSITION_IDENTITY_KEY_DOMAIN, identifier);
      _quantity = quantity;
      _securityKey = securityKey;
    }
    @Override
    public BigDecimal getQuantity() {
      return _quantity;
    }
    @Override
    public Security getSecurity() {
      return null;
    }
    @Override
    public DomainSpecificIdentifiers getSecurityKey() {
      return _securityKey;
    }
    @Override
    public DomainSpecificIdentifier getIdentityKey() {
      return _identityKey;
    }
  }
  
  // TODO this is a slow way of constructing the Node graph - there are a number of recursive queries. One of the bulk fetches could be used and the graph built up from the information in each node
  
  private Position positionBeanToPosition (final PositionMasterSession session, final InstantProvider now, final PositionBean position) {
    final Collection<DomainSpecificIdentifierAssociationBean> assocBeans = session.getDomainSpecificIdentifierAssociationBeanByPosition (now, position);
    final Collection<DomainSpecificIdentifier> dsids = new ArrayList<DomainSpecificIdentifier> (assocBeans.size ());
    for (DomainSpecificIdentifierAssociationBean assocBean : assocBeans) {
      dsids.add (assocBean.getDomainSpecificIdentifier ());
    }
    return new PositionImpl (position.getIdentifier (), position.getQuantity (), new DomainSpecificIdentifiers (dsids));
  }

  private void loadPortfolioNodeChildren (final PositionMasterSession session, final InstantProvider now, final PortfolioNodeImpl node, final PortfolioNodeBean bean) {
    node.setIdentityKey (bean.getIdentifier ());
    for (PortfolioNodeBean child : session.getPortfolioNodeBeanByImmediateAncestor (now, bean)) {
      final PortfolioNodeImpl childNode = new PortfolioNodeImpl (child.getName ());
      loadPortfolioNodeChildren (session, now, childNode, child);
      node.addSubNode (childNode);
    }
    for (final PositionBean position : session.getPositionBeanByImmediatePortfolioNode (now, bean)) {
      node.addPosition (positionBeanToPosition (session, now, position));
    }
  }
  
  public PortfolioNode getPortfolioNode (final InstantProvider now, final DomainSpecificIdentifier identityKey) {
    if (!identityKey.getDomain ().equals (PortfolioNode.PORTFOLIO_NODE_IDENTITY_KEY_DOMAIN)) {
      s_logger.debug ("rejecting invalid identity key domain '{}'", identityKey.getDomain ());
      return null;
    }
    return (PortfolioNode)getHibernateTemplate ().execute (new HibernateCallback () {
      @Override
      public Object doInHibernate (final Session session) throws HibernateException, SQLException {
        final PositionMasterSession positionMasterSession = new PositionMasterSession (session);
        s_logger.info ("retrieve {}", identityKey.getValue ());
        final PortfolioNodeBean bean = positionMasterSession.getPortfolioNodeBeanByIdentifier (now, identityKey.getValue ());
        if (bean == null) {
          s_logger.debug ("bean not found for {} at {}", identityKey, now);
          return null;
        }
        final PortfolioNodeImpl node = new PortfolioNodeImpl (bean.getName ());
        loadPortfolioNodeChildren (positionMasterSession, now, node, bean);
        return node;
      }
    });
  }

  @Override
  public PortfolioNode getPortfolioNode(final DomainSpecificIdentifier identityKey) {
    return getPortfolioNode (TimeSource.system ().instant (), identityKey);
  }
  
  public Position getPosition (final InstantProvider now, final DomainSpecificIdentifier identityKey) {
    if (!identityKey.getDomain ().equals (Position.POSITION_IDENTITY_KEY_DOMAIN)) {
      s_logger.debug ("rejecting invalid identity key domain '{}'", identityKey.getDomain ());
      return null;
    }
    return (Position)getHibernateTemplate ().execute (new HibernateCallback () {
      @Override
      public Object doInHibernate (final Session session) throws HibernateException, SQLException {
        final PositionMasterSession positionMasterSession = new PositionMasterSession (session);
        final PositionBean bean = positionMasterSession.getPositionBeanByIdentifier (now, identityKey.getValue ());
        if (bean == null) {
          s_logger.debug ("bean not found for {} at {}", identityKey, now);
          return null;
        }
        return positionBeanToPosition (positionMasterSession, now, bean);
      }
    });
  }

  @Override
  public Position getPosition(final DomainSpecificIdentifier identityKey) {
    return getPosition (TimeSource.system ().instant (), identityKey);
  }
  
  public Portfolio getRootPortfolio(final InstantProvider now, final String portfolioName) {
    return (Portfolio)getHibernateTemplate ().execute (new HibernateCallback () {
      @Override
      public Object doInHibernate (final Session session) throws HibernateException, SQLException {
        final PositionMasterSession positionMasterSession = new PositionMasterSession (session);
        final Collection<PortfolioBean> beans = positionMasterSession.getPortfolioBeanByName(now, portfolioName);
        if (beans.isEmpty ()) {
          s_logger.debug ("portfolio {} not found at {}", portfolioName, now);
          return null;
        }
        if (beans.size () > 1) {
          s_logger.warn ("{} beans returned for portfolio '{}'", beans.size (), portfolioName);
        }
        final PortfolioBean bean = beans.iterator ().next ();
        final PortfolioImpl portfolio = new PortfolioImpl (bean.getName ());
        loadPortfolioNodeChildren (positionMasterSession, now, portfolio, bean.getRoot ());
        return portfolio;
      }
    });
  }

  @Override
  public Portfolio getRootPortfolio(String portfolioName) {
    return getRootPortfolio (TimeSource.system ().instant (), portfolioName);
  }
  
  @SuppressWarnings("unchecked")
  public Collection<String> getRootPortfolioNames (final InstantProvider now) {
    return (Collection<String>)getHibernateTemplate ().execute (new HibernateCallback () {
      @Override
      public Object doInHibernate (final Session session) throws HibernateException, SQLException {
        final PositionMasterSession positionMasterSession = new PositionMasterSession (session);
        final Collection<PortfolioBean> portfolios = positionMasterSession.getAllPortfolioBeans (now);
        final Collection<String> portfolioNames = new ArrayList<String> ();
        for (PortfolioBean portfolio : portfolios) {
          portfolioNames.add (portfolio.getName ());
        }
        return portfolioNames;
      }
    });
  }
  
  @Override
  public Collection<String> getRootPortfolioNames() {
    return getRootPortfolioNames (TimeSource.system ().instant ());
  }
  
}