package net.truksa.route.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class RoutingService {

    private final DataService dataService;

    public RoutingService(final DataService dataService) {
        this.dataService = dataService;
    }

    public List<String> getRoute(final String origin, final String destination) {
        final var world = dataService.getMapOfCountriesAndBorders();
        final var processedOriginDestination = processOriginDestination(origin, destination, world);
        if (processedOriginDestination.getLeft() == null) {
            return Collections.emptyList();
        }

        final var result = new LinkedList<String>();
        final var visited = new HashSet<String>();
        visited.add(processedOriginDestination.getLeft());
        search(world, result, visited, processedOriginDestination.getLeft(), processedOriginDestination.getRight());
        return result;
    }

    private Pair<String, String> processOriginDestination(
            final String origin,
            final String destination,
            final Map<String, List<String>> world
    ) {
        if (StringUtils.isAllBlank(origin) || StringUtils.isAllBlank(destination)) {
            return Pair.of(null, null);
        }
        final var upperOrigin = origin.toUpperCase();
        final var upperDestination = destination.toUpperCase();
        if (!world.containsKey(upperOrigin) || !world.containsKey(upperDestination)) {
            return Pair.of(null, null);
        }
        return ImmutablePair.of(upperOrigin, upperDestination);
    }

    private boolean search(
            final Map<String, List<String>> world,
            final List<String> result,
            final Set<String> visited,
            final String currentOrigin,
            final String destination
    ) {
        final var nextStops = world.get(currentOrigin);
        if (currentOrigin.equals(destination)) {
            return true;
        } else if (nextStops.contains(destination)) {
            result.add(currentOrigin);
            result.add(destination);
            return true;
        }
        return nextStops.stream()
                .filter(Predicate.not(visited::contains))
                .anyMatch(nextStop -> {
                    visited.add(nextStop);
                    if (search(world, result, visited, nextStop, destination)) {
                        result.add(0, currentOrigin);
                        return true;
                    } else {
                        return false;
                    }
                });
    }

}
