package de.focus_shift.lexoffice.java.sdk.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum TaxType {

    NET("net"),
    GROSS("gross"),
    VATFREE("vatfree"),
    /**
     * Innergemeinschaftliche Lieferung gem. §13b UStG
     */
    INTRA_COMMUNITY_SUPPLY("intraCommunitySupply"),
    /**
     * Bauleistungen gem. §13b UStG
     */
    CONSTRUCTION_SERVICE_13B("constructionService13b"),
    /**
     * Fremdleistungen innerhalb der EU gem. §13b UStG
     */
    EXTERNAL_SERVICE_13B("externalService13b"),
    /**
     * Dienstleistungen an Drittländer
     */
    THIRD_PARTY_COUNTRY_SERVICE("thirdPartyCountryService"),
    /**
     * Ausfuhrlieferungen an Drittländer
     */
    THIRD_PARTY_COUNTRY_DELIVERY("thirdPartyCountryDelivery"),
    /**
     * Photovoltaikanlagen
     */
    PHOTOVOLTAIC_EQUIPMENT("photovoltaicEquipment");

    @Getter
    @JsonValue
    private String value;

    TaxType(String value) {
        this.value = value;
    }
}
