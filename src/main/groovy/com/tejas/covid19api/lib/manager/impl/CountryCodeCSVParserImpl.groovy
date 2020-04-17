package com.tejas.covid19api.lib.manager.impl

import com.tejas.covid19api.lib.constants.DatabaseConstants
import com.tejas.covid19api.lib.manager.CountryCodeCSVParser
import org.springframework.stereotype.Service

@Service
class CountryCodeCSVParserImpl implements CountryCodeCSVParser{
    private Map<String, String> countryCodeMap

    CountryCodeCSVParserImpl() {
        countryCodeMap = new HashMap<>()

        //String csvFile = '../../../../../../../resources/country-codes.csv'
        String csvFile = DatabaseConstants.CSV_FILE
        BufferedReader bufferedReader
        String line
        String cvsSplit = ","
        String[] country

        try {
            bufferedReader = new BufferedReader(new FileReader(csvFile))
            bufferedReader.readLine()
            while ((line = bufferedReader.readLine()) != null) {
                country = line.split(cvsSplit)
                countryCodeMap.put(country[1], country[0])
            }
        }
        catch(Exception e) {
            System.out.println("Error in parsing csv. ${e.getMessage()} ${e.getCause()}")
        }
    }

    @Override
    Map<String, String> getCodeMap() {
        return this.countryCodeMap
    }

}
