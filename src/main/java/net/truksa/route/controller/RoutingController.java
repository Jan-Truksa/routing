package net.truksa.route.controller;

import net.truksa.route.controller.exceptions.ProblematicRequestException;
import net.truksa.route.model.api.RouteResponse;
import net.truksa.route.service.RoutingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("routing")
public class RoutingController {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping(path = "/{origin}/{destination}")
    public RouteResponse findRoute(@PathVariable String origin, @PathVariable String destination) throws ProblematicRequestException {
        final var route = routingService.getRoute(origin, destination);
        if (route.isEmpty()) {
            throw new ProblematicRequestException();
        }
        return new RouteResponse(route);
    }

}
