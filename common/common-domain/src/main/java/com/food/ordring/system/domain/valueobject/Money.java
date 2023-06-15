package com.food.ordring.system.domain.valueobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money implements Serializable, Comparable<Money> {

    private final BigDecimal money;
    private final Currency currency;

    public static final Money ZERO = new Money(BigDecimal.ZERO, Currency.GBP);

    public Money(BigDecimal money, Currency currency) {
        this.money = this.setScale(Objects.requireNonNull(money));
        this.currency = Objects.requireNonNull(currency);
    }

    public Money add(Money money){
        assertMoney(money);
        return new Money(this.money.add(money.getMoney()), this.getCurrency());
    }

    public Money substract(Money money){
        assertMoney(money);
        return new Money(this.money.subtract(money.getMoney()), this.getCurrency());
    }

    public Money multiply(Integer multiplier){
        return new Money(this.money.multiply(new BigDecimal(multiplier)), this.getCurrency());
    }

    public Money multiply(Money money){
        assertMoney(money);
        return new Money(this.money.multiply(money.getMoney()), this.getCurrency());
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Money money1 = (Money) object;
        return money.equals(money1.money) && currency == money1.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, currency);
    }

    @Override
    public int compareTo(Money money) {
        assertMoney(money);
        return this.money.compareTo(money.getMoney());
    }

    private void assertMoney(Money money){
        if (!this.getCurrency().equals(money.getCurrency())){
            throw new IllegalArgumentException("Money object must have same currency");
        }
    }

    private BigDecimal setScale(BigDecimal value){
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Boolean isGreaterThanZero(){
        if(money == null || money.compareTo(BigDecimal.ZERO) < 1 ){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean isGreaterThan(Money money){
        this.assertMoney(money);
        return this.money != null && this.money.compareTo(money.getMoney()) > 0;
    }
}
