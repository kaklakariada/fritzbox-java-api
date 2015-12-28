/**
 * A Java API for managing FritzBox HomeAutomation
 * Copyright (C) 2015 Christoph Pirkl <christoph at users.sourceforge.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.kaklakariada.fritzbox.http;

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

    public Builder newBuilder() {
        return new Builder(new HashMap<>(this.parameters));
    }

    public static class Builder {
        private final HashMap<String, String> parameters;

        private Builder() {
            this(new HashMap<String, String>());
        }

        public Builder(HashMap<String, String> parameters) {
            this.parameters = parameters;
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
