package com.jhipster.demo.visits.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class VisitSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("start_time", table, columnPrefix + "_start_time"));
        columns.add(Column.aliased("end_time", table, columnPrefix + "_end_time"));
        columns.add(Column.aliased("pet_id", table, columnPrefix + "_pet_id"));
        columns.add(Column.aliased("vet_id", table, columnPrefix + "_vet_id"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));

        return columns;
    }
}
