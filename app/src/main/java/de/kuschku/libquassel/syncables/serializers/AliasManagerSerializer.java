/*
 * QuasselDroid - Quassel client for Android
 * Copyright (C) 2016 Janne Koschinski
 * Copyright (C) 2016 Ken Børge Viktil
 * Copyright (C) 2016 Magnus Fjell
 * Copyright (C) 2016 Martin Sandsmark <martin.sandsmark@kde.org>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.kuschku.libquassel.syncables.serializers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.kuschku.libquassel.functions.types.PackedFunction;
import de.kuschku.libquassel.functions.types.SerializedFunction;
import de.kuschku.libquassel.functions.types.UnpackedFunction;
import de.kuschku.libquassel.objects.serializers.ObjectSerializer;
import de.kuschku.libquassel.primitives.QMetaType;
import de.kuschku.libquassel.primitives.types.QVariant;
import de.kuschku.libquassel.syncables.types.impl.AliasManager;
import de.kuschku.libquassel.syncables.types.interfaces.QAliasManager;

import static de.kuschku.util.AndroidAssert.assertNotNull;

@SuppressWarnings("unchecked")
public class AliasManagerSerializer implements ObjectSerializer<AliasManager> {
    @NonNull
    private static final AliasManagerSerializer serializer = new AliasManagerSerializer();

    private AliasManagerSerializer() {

    }

    @NonNull
    public static AliasManagerSerializer get() {
        return serializer;
    }

    @Nullable
    @Override
    public Map<String, QVariant<Object>> toVariantMap(@NonNull AliasManager data) {
        HashMap<String, QVariant<Object>> aliases = new HashMap<>();
        List<String> names = new ArrayList<>(data.aliases().size());
        List<String> expansions = new ArrayList<>(data.aliases().size());
        for (QAliasManager.Alias alias : data.aliases()) {
            names.add(alias.name);
            expansions.add(alias.expansion);
        }
        aliases.put("names", new QVariant(QMetaType.Type.QStringList, names));
        aliases.put("expansions", new QVariant(QMetaType.Type.QStringList, expansions));

        HashMap<String, QVariant<Object>> map = new HashMap<>();
        map.put("Aliases", new QVariant(QMetaType.Type.QVariantMap, aliases));
        return map;
    }

    @NonNull
    @Override
    public AliasManager fromDatastream(@NonNull Map<String, QVariant> map) {
        return fromLegacy(map);
    }

    @NonNull
    @Override
    public AliasManager fromLegacy(@NonNull Map<String, QVariant> map) {
        Map<String, QVariant<List<String>>> aliases = (Map<String, QVariant<List<String>>>) map.get("Aliases").data;
        assertNotNull(aliases);
        return new AliasManager(
                aliases.get("names").data,
                aliases.get("expansions").data
        );
    }

    @Nullable
    @Override
    public AliasManager from(@NonNull SerializedFunction function) {
        if (function instanceof PackedFunction)
            return fromLegacy(((PackedFunction) function).getData());
        else if (function instanceof UnpackedFunction)
            return fromDatastream(((UnpackedFunction) function).getData());
        else throw new IllegalArgumentException();
    }
}
