package com.ntech.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PayTimesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PayTimesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andContypeIsNull() {
            addCriterion("contype is null");
            return (Criteria) this;
        }

        public Criteria andContypeIsNotNull() {
            addCriterion("contype is not null");
            return (Criteria) this;
        }

        public Criteria andContypeEqualTo(String value) {
            addCriterion("contype =", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeNotEqualTo(String value) {
            addCriterion("contype <>", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeGreaterThan(String value) {
            addCriterion("contype >", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeGreaterThanOrEqualTo(String value) {
            addCriterion("contype >=", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeLessThan(String value) {
            addCriterion("contype <", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeLessThanOrEqualTo(String value) {
            addCriterion("contype <=", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeLike(String value) {
            addCriterion("contype like", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeNotLike(String value) {
            addCriterion("contype not like", value, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeIn(List<String> values) {
            addCriterion("contype in", values, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeNotIn(List<String> values) {
            addCriterion("contype not in", values, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeBetween(String value1, String value2) {
            addCriterion("contype between", value1, value2, "contype");
            return (Criteria) this;
        }

        public Criteria andContypeNotBetween(String value1, String value2) {
            addCriterion("contype not between", value1, value2, "contype");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("begin_time is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(Date value) {
            addCriterionForJDBCDate("begin_time =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("begin_time <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("begin_time >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_time >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(Date value) {
            addCriterionForJDBCDate("begin_time <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("begin_time <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<Date> values) {
            addCriterionForJDBCDate("begin_time in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("begin_time not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_time between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("begin_time not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andTotalTimesIsNull() {
            addCriterion("total_times is null");
            return (Criteria) this;
        }

        public Criteria andTotalTimesIsNotNull() {
            addCriterion("total_times is not null");
            return (Criteria) this;
        }

        public Criteria andTotalTimesEqualTo(Integer value) {
            addCriterion("total_times =", value, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesNotEqualTo(Integer value) {
            addCriterion("total_times <>", value, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesGreaterThan(Integer value) {
            addCriterion("total_times >", value, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_times >=", value, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesLessThan(Integer value) {
            addCriterion("total_times <", value, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesLessThanOrEqualTo(Integer value) {
            addCriterion("total_times <=", value, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesIn(List<Integer> values) {
            addCriterion("total_times in", values, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesNotIn(List<Integer> values) {
            addCriterion("total_times not in", values, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesBetween(Integer value1, Integer value2) {
            addCriterion("total_times between", value1, value2, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andTotalTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("total_times not between", value1, value2, "totalTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesIsNull() {
            addCriterion("left_times is null");
            return (Criteria) this;
        }

        public Criteria andLeftTimesIsNotNull() {
            addCriterion("left_times is not null");
            return (Criteria) this;
        }

        public Criteria andLeftTimesEqualTo(Integer value) {
            addCriterion("left_times =", value, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesNotEqualTo(Integer value) {
            addCriterion("left_times <>", value, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesGreaterThan(Integer value) {
            addCriterion("left_times >", value, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("left_times >=", value, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesLessThan(Integer value) {
            addCriterion("left_times <", value, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesLessThanOrEqualTo(Integer value) {
            addCriterion("left_times <=", value, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesIn(List<Integer> values) {
            addCriterion("left_times in", values, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesNotIn(List<Integer> values) {
            addCriterion("left_times not in", values, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesBetween(Integer value1, Integer value2) {
            addCriterion("left_times between", value1, value2, "leftTimes");
            return (Criteria) this;
        }

        public Criteria andLeftTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("left_times not between", value1, value2, "leftTimes");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}