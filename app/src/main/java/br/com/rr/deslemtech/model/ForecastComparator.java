package br.com.rr.deslemtech.model;

import java.util.Comparator;

import br.com.rr.deslemtech.model.domain.Forecast;

public class ForecastComparator implements Comparator<Forecast> {

    @Override
    public int compare(Forecast lhs, Forecast rhs) {

        if(lhs != null && rhs != null) {

            Long lhsDate = Long.parseLong(lhs.date);
            Long rhsDate = Long.parseLong(rhs.date);

            return lhsDate.compareTo(rhsDate);
        }
        else if(lhs == null) {
                return -1;
        }
        else if(rhs == null) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
