/*
 * Copyright 2015 Rahel Lüthy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.netzwerg.paleo;

import javaslang.collection.Array;
import javaslang.collection.List;
import javaslang.collection.Stream;

import static ch.netzwerg.paleo.ColumnIds.StringColumnId;

public final class StringColumn extends GenericColumn<String, StringColumnId> {

    private StringColumn(StringColumnId id, Array<String> values) {
        super(id, values);
    }

    public static StringColumn of(StringColumnId id, String value) {
        return builder(id).add(value).build();
    }

    public static StringColumn ofAll(StringColumnId id, String... values) {
        return builder(id).addAll(values).build();
    }

    public static StringColumn ofAll(StringColumnId id, Iterable<String> values) {
        return builder(id).addAll(values).build();
    }

    public static Builder builder(StringColumnId id) {
        return new Builder(id, List.empty());
    }

    public static final class Builder implements Column.Builder<String, StringColumn> {

        private final StringColumnId id;
        private final List<String> acc;

        private Builder(StringColumnId id, List<String> acc) {
            this.id = id;
            this.acc = acc;
        }

        @Override
        public Builder add(String value) {
            return new Builder(id, acc.prepend(value));
        }

        public Builder addAll(String... values) {
            return addAll(Stream.ofAll(values));
        }

        public Builder addAll(Iterable<String> values) {
            return new Builder(id, acc.prependAll(List.ofAll(values).reverse()));
        }

        @Override
        public StringColumn build() {
            return new StringColumn(id, acc.reverse().toArray());
        }

    }

}