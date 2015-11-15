package com.github.fritzbox.http;

import java.util.HashMap;

public class QueryParameters {

    private final HashMap<String, String> parameters;

    private QueryParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public static Builder builder() {
        return new Builder();
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public static class Builder {
        private final HashMap<String, String> parameters;

        private Builder() {
            parameters = new HashMap<>();
        }

        public Builder add(String name, String value) {
            parameters.put(name, value);
            return this;
        }

        public QueryParameters build() {
            return new QueryParameters(parameters);
        }
    }
}
