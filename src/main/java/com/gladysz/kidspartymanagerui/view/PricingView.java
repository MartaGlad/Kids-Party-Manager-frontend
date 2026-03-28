package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.*;
import com.gladysz.kidspartymanagerui.service.*;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


@PageTitle("Price preview")
@Route(layout = MainLayout.class)
public class PricingView extends VerticalLayout {

    private final ComboBox<EventPackageResponseDto> eventPackageComboBox;
    private final DatePicker eventDateField;
    private final IntegerField childrenCountField;
    private final VerticalLayout spanLayout;
    private Span finalPricePln, priceInEuro, priceInUsd, priceInGbp, holiday;

    private static final Logger LOGGER = LoggerFactory.getLogger(PricingView.class);

    public PricingView(
            PricingPreviewService pricingPreviewService,
            EventPackageService eventPackageService) {

        H1 title  = new H1("Pricing preview");
        title.getStyle()
                .set("font-size", "30px")
                .set("font-weight", "bold");

        eventPackageComboBox = new ComboBox<>("Event package");
        eventPackageComboBox.setWidthFull();
        eventPackageComboBox.setRequired(true);

        try {
            eventPackageComboBox.setItems(eventPackageService.getAllEventPackages());
        } catch (Exception e) {
            Notification.show("Failed to load event packages.");
            LOGGER.error("Failed to load data from backend ", e);
        }

        eventPackageComboBox.setItemLabelGenerator(item
                -> item.name() + " - base price: " + item.basePrice().toPlainString() + " PLN");

        childrenCountField = new IntegerField("Number of children");
        childrenCountField.setWidthFull();
        childrenCountField.setMin(1);
        childrenCountField.setMax(20);
        childrenCountField.setRequired(true);
        childrenCountField.setErrorMessage("Please enter a number between 1 and 20");

        eventDateField = new DatePicker("Event date");
        eventDateField.setWidthFull();
        eventDateField.setMin(LocalDate.now());
        eventDateField.setRequired(true);

        spanLayout = new VerticalLayout();
        spanLayout.setWidth("400px");

        Button calculateButton = new Button("Calculate", ev -> {

            if(!validateInput()) {
                return;
            }

            PricingRequestDto requestDto = new PricingRequestDto(
                    eventPackageComboBox.getValue().id(),
                    childrenCountField.getValue(),
                    eventDateField.getValue()
            );

            try {
                PricingResultDto resultDto = pricingPreviewService.getPricingPreview(requestDto);
                finalPricePln.setText("PLN: " + resultDto.finalPricePln().toPlainString());
                finalPricePln.getStyle().setFontWeight("bold");
                priceInEuro.setText("EUR: " + resultDto.priceInEur().toPlainString());
                priceInUsd.setText("USD: " + resultDto.priceInUsd().toPlainString());
                priceInGbp.setText("GBP: " + resultDto.priceInGbp().toPlainString());
                holiday.setText(resultDto.holiday() ? "Holiday: YES" : "Holiday: NO");
                spanLayout.setVisible(true);
            } catch (Exception e) {
                Notification.show("Failed to calculate price.");
                LOGGER.error("Failed to load data from backend ", e);
            }
        });

        finalPricePln = new Span();
        priceInEuro = new Span();
        priceInUsd = new Span();
        priceInGbp = new Span();
        holiday = new Span();

        H3 resultTitle = new H3("Pricing result");

        spanLayout.add(resultTitle, finalPricePln, priceInEuro, priceInUsd, priceInGbp, holiday);
        spanLayout.setVisible(false);

        Button clearButton = new Button("Clear", e -> {
            eventPackageComboBox.clear();
            childrenCountField.clear();
            eventDateField.clear();

            spanLayout.setVisible(false);
        });

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(calculateButton, clearButton);
        buttonLayout.getStyle().setMarginTop("20px");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setWidth("400px");

        formLayout.add(eventPackageComboBox, childrenCountField,
                eventDateField, buttonLayout);

        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setWidth("100%");
        contentLayout.add(formLayout, spanLayout);
        contentLayout.getStyle().set("gap", "50px");

        add(title, contentLayout);
    }


    private boolean validateInput() {

        if (eventPackageComboBox.getValue() == null) {
            Notification.show("Please choose event package.");
            return false;
        }

        if (childrenCountField.getValue() == null) {
            Notification.show("Please enter children count.");
            return false;
        }

        if (childrenCountField.getValue() <= 0 || childrenCountField.getValue() > 20) {
            Notification.show("Children count must be between 1 and 20.");
            return false;
        }

        if (eventDateField.getValue() == null) {
            Notification.show("Please enter event date.");
            return false;
        }
        return true;
    }
}



