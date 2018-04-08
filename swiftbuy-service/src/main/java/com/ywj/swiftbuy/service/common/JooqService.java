package com.ywj.swiftbuy.service.common;

import com.ywj.swiftbuy.service.utils.RecordMapperUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class JooqService {

    protected static final int INVALID_ID = -1;

    @Resource
    private DSLContext jooqContext;

    protected static Condition TRUE = DSL.trueCondition();

    protected interface PostProcessor {
        boolean process();
    }

    protected interface PostProcessorWithReturn {
        boolean process(Record record);
    }

    protected DSLContext getJooqContext() {
        return jooqContext;
    }

    protected <T> T recordToObject(Record record, Class<T> targetClass) {
        return RecordMapperUtils.mapObject(record, targetClass);
    }

    protected <T> T recordToObject(Record record, RecordMapper<Record, T> recordMapper) {
        return record != null ? record.map(recordMapper) : null;
    }

    protected <T> T objectToRecord(Object object, Class<T> targetClass) {
        return RecordMapperUtils.mapObject(object, targetClass);
    }

    protected <T, R extends Record> List<T> toList(Result<R> result, Class<T> targetClass) {
        if (result == null) {
            return Collections.EMPTY_LIST;
        }

        return toList(result, r -> {
            return recordToObject(r, targetClass);
        });
    }

    protected <T, R extends Record> List<T> toList(Result<R> result, RecordMapper<R, T> recordMapper) {
        if (result == null) {
            return Collections.EMPTY_LIST;
        }

        return result.map(recordMapper);
    }

    protected static Map<Field<?>, Object> generateFieldsToUpdate(Record record, Field<?> fieldToFilter) {
        final Map<Field<?>, Object> toUpdate = new HashMap<>();

        for (Field<?> f : record.fields()) {
            if (f == fieldToFilter) {
                continue;
            }
            final Object v = record.getValue(f);
            toUpdate.put(f, v);
        }
        return toUpdate;
    }

    protected static Map<Field<?>, Object> generateFieldsWithoutFilter(Record record, List<Field<?>> fieldsToUpdate) {
        final Map<Field<?>, Object> toUpdate = new HashMap<>();

        for (Field<?> f : record.fields()) {
            if (!fieldsToUpdate.contains(f)) {
                continue;
            }
            final Object v = record.getValue(f);
            toUpdate.put(f, v);
        }
        return toUpdate;
    }

    protected static Map<Field<?>, Object> generateFieldsToUpdate(Record record, List<Field<?>> fieldsToFilter) {
        final Map<Field<?>, Object> toUpdate = new HashMap<>();

        for (Field<?> f : record.fields()) {
            if (fieldsToFilter.contains(f)) {
                continue;
            }
            final Object v = record.getValue(f);
            toUpdate.put(f, v);
        }
        return toUpdate;
    }

    protected <T, R extends Record> T selectOneValue(Table<R> table, Field<T> field, Condition condition) {
        Record record = selectOneRecord(table, condition);
        return record == null ? null : record.getValue(field);
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Field<T> field, Condition condition) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .fetch(field);
    }

    protected <R extends Record> R selectOneRecord(Table<R> table, Condition condition) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .fetchOne();
    }

    protected <T, R extends Record> T selectOneRecord(Table<R> table, Condition condition, Class<T> targetClass) {
        return recordToObject(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .fetchOne(),
                targetClass
        );
    }

    protected <T, R extends Record> T selectOneRecord(Table<R> table, Condition condition, RecordMapper<Record, T> recordMapper) {
        return recordToObject(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .fetchOne(),
                recordMapper
        );
    }

    protected <R extends Record> List<R> select(Table<R> table, Condition condition) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .fetch();
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, Class<T> targetClass) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .fetch(),
                targetClass
        );
    }
    protected <T, R extends Record> List<T> selectAll(Table<R> table,  Class<T> targetClass) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .fetch(),
                targetClass
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, RecordMapper<R, T> recordMapper) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .fetch(),
                recordMapper
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Field<T> field, Condition condition, SortField<?> sortField) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .orderBy(sortField)
                .fetch(field);
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, SortField<?> sortField, Class<T> targetClass) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortField)
                        .fetch(),
                targetClass
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, SortField<?> sortField, int limit, Class<T> targetClass) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortField)
                        .limit(limit)
                        .fetch(),
                targetClass
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, SortField<?> sortField, int limit, int offset, Class<T> targetClass) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortField)
                        .limit(limit)
                        .offset(offset)
                        .fetch(),
                targetClass
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, SortField<?> sortFirstField, SortField<?> sortSecondField, int limit, int offset, Class<T> targetClass) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortFirstField, sortSecondField)
                        .limit(limit)
                        .offset(offset)
                        .fetch(),
                targetClass
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, SortField<?> sortField, int limit, int offset, RecordMapper<R, T> recordMapper) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortField)
                        .limit(limit)
                        .offset(offset)
                        .fetch(),
                recordMapper
        );
    }

    protected <R extends Record> R selectTopOne(Table<R> table, Condition condition, SortField<?> sortField) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .orderBy(sortField)
                .limit(1)
                .fetchOne();
    }

    protected <T, R extends Record> T selectTopOne(Table<R> table, Condition condition, SortField<?> sortField, Class<T> targetClass) {
        return recordToObject(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortField)
                        .limit(1)
                        .fetchOne(),
                targetClass
        );
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Field<T> field, Condition condition, SortField<?> sortField, int limit, int offset) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .orderBy(sortField)
                .limit(limit)
                .offset(offset)
                .fetch(field);
    }

    protected <T, R extends Record> List<T> select(Table<R> table, Condition condition, SortField<?> sortField, RecordMapper<R, T> recordMapper) {
        return toList(
                getJooqContext()
                        .selectFrom(table)
                        .where(condition)
                        .orderBy(sortField)
                        .fetch(),
                recordMapper
        );
    }

    protected <R extends Record> int fetchCount(Table<R> table) {
        return getJooqContext()
                .fetchCount(table);
    }

    protected <R extends Record> int fetchCount(Table<R> table, Condition condition) {
        return getJooqContext()
                .fetchCount(table, condition);
    }

    protected <R extends Record> boolean exists(Table<R> table, Condition condition) {
        return getJooqContext()
                .selectFrom(table)
                .where(condition)
                .fetch()
                .isNotEmpty();
    }

    protected <R extends Record> boolean insert(Table<R> table, Record record) {
        return getJooqContext()
                .insertInto(table)
                .set(record)
                .execute() == 1;
    }

    protected <R extends Record> boolean insert(Table<R> table, Map<Field<?>, Object> fields) {
        return getJooqContext()
                .insertInto(table)
                .set(fields)
                .execute() == 1;
    }

    protected <R extends Record> Record insertWithReturning(Table<R> table, Record record, Field field) {
        return getJooqContext()
                .insertInto(table)
                .set(record)
                .returning(field)
                .fetchOne();
    }

    protected <R extends Record> boolean update(Table<R> table, Record record, Condition condition) {
        return getJooqContext()
                .update(table)
                .set(record)
                .where(condition)
                .execute() == 1;
    }

    protected <R extends Record> boolean update(Table<R> table, Map<Field<?>, Object> toUpdate, Condition condition) {
        return getJooqContext()
                .update(table)
                .set(toUpdate)
                .where(condition)
                .execute() >= 1;
    }

    protected <T, R extends Record> boolean updateField(Table<R> table, Field<T> field, T value, Condition condition) {
        return getJooqContext()
                .update(table)
                .set(field, value)
                .where(condition)
                .execute() >= 1;
    }

    protected <T, R extends Record> boolean updateField(Table<R> table, Field<T> field, Field<T> value, Condition condition) {
        return getJooqContext()
                .update(table)
                .set(field, value)
                .where(condition)
                .execute() >= 1;
    }

    protected <R extends Record> boolean delete(Table<R> table, Condition condition) {
        return getJooqContext()
                .delete(table)
                .where(condition)
                .execute() >= 1;
    }
}
