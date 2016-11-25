/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safara.watchdog.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.safara.watchdog.common.LogUtil;
import com.safara.watchdog.entity.HeartBeatEntity;
import com.safara.watchdog.exception.HeartBeatDaoException;

/**
 *
 * @author boyot
 */
public class HeartBeatDao {

    private static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("watch-dogPU");
    private EntityManager em;
    private static final Logger log = new LogUtil(HeartBeatDao.class.getName()).getLogger();

    public HeartBeatDao() {
        
        this.em = emf.createEntityManager();

    }

    public void save(HeartBeatEntity heartbeatEntity) throws HeartBeatDaoException {
        log.log(Level.INFO, "HeartBeatDao.save"+heartbeatEntity);
        try {
            em.getTransaction().begin();
            em.persist(heartbeatEntity);
            em.getTransaction().commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, "HeartBeatDao.save.Exception", e);
            throw  new HeartBeatDaoException("Unable to persist entity");
        }
    }

    public List<HeartBeatEntity> findByDate(Timestamp fromDate, Timestamp toDate) {

        List<HeartBeatEntity> heartBeatEntitys = new ArrayList<>();

        em.getTransaction().begin();

        heartBeatEntitys = em.createQuery("SELECT h FROM HeartBeatEntity h WHERE h.time BETWEEN :startDate AND :endDate")
                .setParameter("startDate", fromDate)
                .setParameter("endDate", toDate)
                .getResultList();

        em.getTransaction().commit();

        return heartBeatEntitys;
    }

    public List<HeartBeatEntity> findByDateCriteria(Timestamp fromDate, Timestamp toDate) {

        List<HeartBeatEntity> heartBeatEntitys = new ArrayList<>();

        em.getTransaction().begin();

        heartBeatEntitys = em.createQuery("SELECT h FROM HeartBeatEntity h WHERE h.time BETWEEN :startDate AND :endDate")
                .setParameter("startDate", fromDate)
                .setParameter("endDate", toDate)
                .getResultList();

        em.getTransaction().commit();

        return heartBeatEntitys;
    }

}
