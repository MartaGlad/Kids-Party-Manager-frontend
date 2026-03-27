package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.AnimatorResponseDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import com.gladysz.kidspartymanagerui.service.AnimatorService;
import com.gladysz.kidspartymanagerui.service.EventPackageService;
import com.gladysz.kidspartymanagerui.service.OrdererService;
import com.gladysz.kidspartymanagerui.service.ReservationService;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@PageTitle("Kids Party Manager")
@Route(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardView.class);


    public DashboardView(
            AnimatorService animatorService,
            OrdererService ordererService,
            EventPackageService eventPackageService,
            ReservationService reservationService) {

        H2 welcome = new H2("Welcome to Kids Party Manager!");
        welcome.getStyle().set("font-weight", "bold");

        Span reservationsOverviewSpan = new Span("Reservations overview");
        reservationsOverviewSpan.getStyle()
                .set("font-size", "20px")
                .set("font-weight", "bold");

        Span resourcesSpan = new Span("Resources");
        resourcesSpan.getStyle()
                .set("font-size", "20px")
                .set("font-weight", "bold");

        int newReservationsCount = 0, confirmedReservationsCount = 0, completedReservationsCount = 0,
            cancelledReservationsCount = 0, animatorsCount = 0, activeAnimatorsCount = 0,
            eventPackagesCount = 0, orderersCount = 0;

        try {
            newReservationsCount = reservationService.getReservations(Status.NEW, null, null).size();
            confirmedReservationsCount = reservationService.getReservations(Status.CONFIRMED, null, null).size();
            completedReservationsCount = reservationService.getReservations(Status.COMPLETED, null, null).size();
            cancelledReservationsCount = reservationService.getReservations(Status.CANCELLED, null, null).size();

            List<AnimatorResponseDto> animators = animatorService.getAllAnimators();
            animatorsCount = animators.size();
            activeAnimatorsCount = animators
                    .stream()
                    .filter(AnimatorResponseDto::active)
                    .toList().size();

            eventPackagesCount = eventPackageService.getAllEventPackages().size();
            orderersCount = ordererService.getAllOrderers().size();

        } catch (Exception e) {
            Notification.show("Could not load data from backend.");
            LOGGER.error("Could not load dashboard statistics ", e);
        }

        VerticalLayout newReservationsLayout = createStatCard("NEW", String.valueOf(newReservationsCount));
        VerticalLayout confirmedReservationsLayout = createStatCard("CONFIRMED", String.valueOf(confirmedReservationsCount));
        VerticalLayout completedReservationsLayout = createStatCard("COMPLETED", String.valueOf(completedReservationsCount));
        VerticalLayout cancelledReservationsLayout = createStatCard("CANCELLED", String.valueOf(cancelledReservationsCount));

        VerticalLayout reservationsStatisticsLayout = new VerticalLayout();
        reservationsStatisticsLayout.add(reservationsOverviewSpan, newReservationsLayout,
                confirmedReservationsLayout, completedReservationsLayout, cancelledReservationsLayout);


        VerticalLayout animatorsLayout = createStatCard("ANIMATORS", String.valueOf(animatorsCount));
        VerticalLayout activeAnimatorsLayout = createStatCard("ACTIVE ANIMATORS", String.valueOf(activeAnimatorsCount));
        VerticalLayout packagesLayout = createStatCard("EVENT PACKAGES", String.valueOf(eventPackagesCount));
        VerticalLayout orderersLayout = createStatCard("ORDERERS", String.valueOf(orderersCount));

        VerticalLayout resourcesStatisticsLayout = new VerticalLayout();
        resourcesStatisticsLayout.add(resourcesSpan, animatorsLayout, activeAnimatorsLayout, packagesLayout, orderersLayout);

        HorizontalLayout stats = new HorizontalLayout();
        stats.setWidthFull();

        stats.add(reservationsStatisticsLayout, resourcesStatisticsLayout);

        add(welcome, stats);
    }


    private VerticalLayout createStatCard(String label, String value) {

        Span spanLabel = new Span(label);
        spanLabel.getStyle().set("font-weight", "bold");

        H2 headerValue = new H2(value);

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        String backgroundColor = switch (label) {
            case "NEW" -> "#fef3c7";
            case "CONFIRMED" -> "#dbeafe";
            case "COMPLETED" -> "#dcfce7";
            case "CANCELLED" -> "#fee2e2";
            default -> "#f3f4f6";
        };

        layout.getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "15px")
                .set("min-width", "180px")
                .set("background-color", backgroundColor);

        layout.add(spanLabel, headerValue);

        return layout;
    }
}
