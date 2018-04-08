package com.ywj.swiftbuy.service.common;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class CommonService extends JooqService {

    private static final Logger LOG = LoggerFactory.getLogger(CommonService.class);

    protected <R extends Record> boolean insert(Table<R> table, Record record, PostProcessor postProcessor) {
        boolean success = super.insert(table, record);
        if (!success) {
            LOG.error("insert failed, table={}, record={}", table, record);
            return false;
        }

        return postProcessor == null || postProcessor.process();
    }

    protected <R extends Record> boolean insert(Table<R> table, Map<Field<?>, Object> fields, PostProcessor postProcessor) {
        boolean success = super.insert(table, fields);
        if (!success) {
            LOG.error("insert failed, table={}, fields={}", table, fields);
            return false;
        }

        return postProcessor == null || postProcessor.process();
    }

    protected <R extends Record> boolean insert(Table<R> table, Record record, Field field, PostProcessorWithReturn postProcessor) {
        Record record1 = super.insertWithReturning(table, record, field);
        if (record1 == null) {
            LOG.error("insert failed, table={}, record={}", table, record);
            return false;
        }
        return postProcessor == null || postProcessor.process(record1);
    }

    protected <R extends Record> Record insertWithReturning(Table<R> table, Record record, Field field, PostProcessor postProcessor) {
        Record record1 = super.insertWithReturning(table, record, field);
        if (record1 == null) {
            LOG.error("insert failed, table={}, record={}", table, record);
            return null;
        }
        if (postProcessor != null) {
            postProcessor.process();
        }

        return record1;
    }

    protected <R extends Record> Record insertWithReturningUsingRecord(Table<R> table, Record record, Field field, PostProcessorWithReturn postProcessor) {
        Record record1 = super.insertWithReturning(table, record, field);
        if (record1 == null) {
            LOG.error("insert failed, table={}, record={}", table, record);
            return null;
        }
        if (postProcessor != null) {
            postProcessor.process(record1);
        }

        return record1;
    }

    protected <R extends Record> boolean update(Table<R> table, Record record, Condition condition, PostProcessor postProcessor) {
        boolean success = super.update(table, record, condition);
        if (!success) {
            LOG.error("update failed, table={}, record={}, condition={}", table, record, condition);
            return false;
        }

        return postProcessor == null || postProcessor.process();
    }

    protected <R extends Record> boolean update(Table<R> table, Map<Field<?>, Object> toUpdate, Condition condition, PostProcessor postProcessor) {
        boolean success = super.update(table, toUpdate, condition);
        if (!success) {
            LOG.error("update failed, table={}, toUpdate={}, condition={}", table, toUpdate, condition);
            return false;
        }

        return postProcessor == null || postProcessor.process();
    }

    protected <T, R extends Record> boolean updateField(Table<R> table, Field<T> field, T value, Condition condition, PostProcessor postProcessor) {
        boolean success = super.updateField(table, field, value, condition);
        if (!success) {
            LOG.error("updateField failed, table={}, field={}, value={}, condition={}", table, field, value, condition);
            return false;
        }

        return postProcessor == null || postProcessor.process();
    }

    protected <R extends Record> boolean delete(Table<R> table, Condition condition, PostProcessor postProcessor) {
        boolean success = super.delete(table, condition);
        if (!success) {
            LOG.error("delete failed, table={}, condition={}", table, condition);
            return false;
        }

        return postProcessor == null || postProcessor.process();
    }

}
