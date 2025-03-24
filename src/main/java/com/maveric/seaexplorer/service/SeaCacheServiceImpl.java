package com.maveric.seaexplorer.service;

import com.maveric.seaexplorer.controller.req.Coordinate;
import com.maveric.seaexplorer.repo.ProbeVisitHistoryRepo;
import com.maveric.seaexplorer.repo.SeaObstacleRepo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeaCacheServiceImpl implements SeaCacheService, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    private SeaObstacleRepo obstacleRepo;

    @Autowired
    private ProbeVisitHistoryRepo probeVisitHistoryRepo;

    @Override
    public Coordinate getSeaCoordinates(){
        return new Coordinate(10, 10);
    }

    @Override
    @Cacheable(value = "sea-obstacles")
    @EventListener(ApplicationReadyEvent.class)
    public Set<String> loadObstacles(){
        return Optional.ofNullable(obstacleRepo.findAll()).orElse(new ArrayList<>())
                .stream()
                .map(o -> getIdByCoordinates(new Coordinate(o.getX(), o.getY())))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    @Cacheable(value = "sea-obstacles")
    public Set<String> getSeaObstacles() {
        return null;
    }

    @Override
    @Cacheable
    public Set<String> updateObstacles(Coordinate coordinate) {
        var cache = beanFactory.getBean(SeaCacheService.class)
                .getSeaObstacles();
        var newCache = new HashSet<String>(cache);
        newCache.add(getIdByCoordinates(coordinate));
        return Collections.unmodifiableSet(newCache);
    }

    @Override
    public String getIdByCoordinates(Coordinate coordinate) {
        return "%d/%d".formatted(coordinate.getX(), coordinate.getY());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

