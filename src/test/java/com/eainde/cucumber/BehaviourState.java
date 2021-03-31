package com.eainde.cucumber;

import io.cucumber.spring.CucumberTestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class BehaviourState {
    private final Map<String, Object> stateMap= new HashMap<>();

    public <T> T putResult(final String stateName, final Supplier<T> supplier){
        final T result =supplier.get();
        stateMap.put(stateName,result);
        return result;
    }

    public <T> T put(final String stateName, final T value){
        stateMap.put(stateName, value);
        return value;
    }

    public <T> T fetchValue(final String name, final Class<T> clazz){
        return clazz.cast(fetchValue(name));
    }

    public Object fetchValue(final String name){
        return stateMap.get(name);
    }

    public <T,K> K map(final String stateName, final Class<T> clazz, final Function<T , K> mapper){
        final K mappedValue=
                Optional.ofNullable(stateMap.get(stateName))
                    .map(value -> mapper.apply(clazz.cast(value)))
                    .orElseThrow();
        stateMap.put(stateName, mappedValue);
        return mappedValue;
    }

    public void clear(){
        stateMap.clear();
    }
}
