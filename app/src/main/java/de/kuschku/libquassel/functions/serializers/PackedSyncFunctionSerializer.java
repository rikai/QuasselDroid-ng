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

package de.kuschku.libquassel.functions.serializers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.kuschku.libquassel.functions.FunctionType;
import de.kuschku.libquassel.functions.types.SyncFunction;
import de.kuschku.libquassel.primitives.QMetaType;
import de.kuschku.libquassel.primitives.types.QVariant;
import de.kuschku.libquassel.protocols.DatastreamPeer;

import static de.kuschku.util.AndroidAssert.assertTrue;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class PackedSyncFunctionSerializer<T> implements FunctionSerializer<SyncFunction<T>> {
    @NonNull
    private static final PackedSyncFunctionSerializer serializer = new PackedSyncFunctionSerializer();

    private PackedSyncFunctionSerializer() {
    }

    @NonNull
    public static PackedSyncFunctionSerializer get() {
        return serializer;
    }

    @NonNull
    @Override
    public List serialize(@NonNull final SyncFunction data) {
        final List func = new ArrayList<>();
        func.add(new QVariant<>(QMetaType.Type.Int, FunctionType.SYNC.id));
        func.add(new QVariant<>(QMetaType.Type.QByteArray, data.className));
        func.add(new QVariant<>(QMetaType.Type.QByteArray, data.objectName));
        func.add(new QVariant<>(QMetaType.Type.QByteArray, data.methodName));
        func.addAll(data.params);
        return func;
    }

    @NonNull
    @Override
    public SyncFunction<T> deserialize(@NonNull final List packedFunc) {
        assertTrue(packedFunc.size() >= 3);

        return new SyncFunction<>(
                ((QVariant<String>) packedFunc.remove(0)).data,
                ((QVariant<String>) packedFunc.remove(0)).data,
                ((QVariant<String>) packedFunc.remove(0)).data,
                DatastreamPeer.unboxList(packedFunc)
        );
    }
}
