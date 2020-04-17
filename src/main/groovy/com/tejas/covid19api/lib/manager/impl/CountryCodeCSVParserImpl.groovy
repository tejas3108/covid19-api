package com.tejas.covid19api.lib.manager.impl

import com.tejas.covid19api.lib.manager.CountryCodeCSVParser
import org.springframework.stereotype.Service

@Service
class CountryCodeCSVParserImpl implements CountryCodeCSVParser{
    Map<String, String> countryCodeMap

    CountryCodeCSVParserImpl() {
        countryCodeMap = new HashMap<>()


    }

    @Override
    Map<String, String> getCountryCodeMap() {
        return this.countryCodeMap
    }

}
