package com.jhipster.demo.visits.repository.rowmapper;

import com.jhipster.demo.visits.domain.Visit;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Visit}, with proper type conversions.
 */
@Service
public class VisitRowMapper implements BiFunction<Row, String, Visit> {

    private final ColumnConverter converter;

    public VisitRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Visit} stored in the database.
     */
    @Override
    public Visit apply(Row row, String prefix) {
        Visit entity = new Visit();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setVisitDate(converter.fromRow(row, prefix + "_visit_date", LocalDate.class));
        entity.setPetId(converter.fromRow(row, prefix + "_pet_id", Long.class));
        entity.setVetId(converter.fromRow(row, prefix + "_vet_id", Long.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        return entity;
    }
}
