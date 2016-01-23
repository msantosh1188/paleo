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

import ch.netzwerg.paleo.util.LinkedHashMapUtil;
import javaslang.collection.Array;
import javaslang.collection.Map;
import javaslang.collection.Stream;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

import static ch.netzwerg.paleo.ColumnIds.TimestampColumnId;

public final class TimestampColumn extends AbstractColumn<Instant, TimestampColumnId> {

    private TimestampColumn(TimestampColumnId id, Array<Instant> values, Map<String, String> metaData) {
        super(id, values, metaData);
    }

    public static TimestampColumn of(TimestampColumnId id, Instant value) {
        return builder(id).add(value).build();
    }

    public static TimestampColumn ofAll(TimestampColumnId id, Instant... values) {
        return builder(id).addAll(values).build();
    }

    public static TimestampColumn ofAll(TimestampColumnId id, Iterable<Instant> values) {
        return builder(id).addAll(values).build();
    }

    public static Builder builder(TimestampColumnId id) {
        return new Builder(id);
    }

    public static final class Builder implements Column.Builder<Instant, TimestampColumn> {

        private final TimestampColumnId id;
        private final java.util.List<Instant> values;
        private final java.util.Map<String, String> metaData;

        private Builder(TimestampColumnId id) {
            this.id = id;
            this.values = new ArrayList<>();
            this.metaData = new java.util.LinkedHashMap<>();
        }

        @Override
        public Builder add(Instant value) {
            values.add(value);
            return this;
        }

        public Builder addAll(Instant... values) {
            return addAll(Stream.of(values));
        }

        public Builder addAll(Iterable<Instant> values) {
            this.values.addAll(Stream.ofAll(values).toJavaList());
            return this;
        }

        @Override
        public Builder withMetaData(Map<String, String> metaData) {
            Objects.requireNonNull(metaData, "metaData is null");
            this.metaData.clear();
            metaData.forEach(t -> this.metaData.put(t._1, t._2));
            return this;
        }

        @Override
        public TimestampColumn build() {
            return new TimestampColumn(id, Array.ofAll(values), LinkedHashMapUtil.ofAll(metaData));
        }

    }

}
