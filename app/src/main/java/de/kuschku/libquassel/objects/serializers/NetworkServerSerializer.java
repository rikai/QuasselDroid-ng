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

package de.kuschku.libquassel.objects.serializers;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import de.kuschku.libquassel.functions.types.PackedFunction;
import de.kuschku.libquassel.functions.types.SerializedFunction;
import de.kuschku.libquassel.functions.types.UnpackedFunction;
import de.kuschku.libquassel.objects.types.NetworkServer;
import de.kuschku.libquassel.primitives.QMetaType;
import de.kuschku.libquassel.primitives.types.QVariant;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class NetworkServerSerializer implements ObjectSerializer<NetworkServer> {
    @NonNull
    private static final NetworkServerSerializer serializer = new NetworkServerSerializer();

    private NetworkServerSerializer() {
    }

    @NonNull
    public static NetworkServerSerializer get() {
        return serializer;
    }

    @NonNull
    @Override
    public Map<String, QVariant<Object>> toVariantMap(@NonNull NetworkServer data) {
        final Map<String, QVariant<Object>> map = new HashMap<>();

        map.put("UseSSL", new QVariant<>(QMetaType.Type.Bool, data.useSSL));
        map.put("sslVersion", new QVariant<>(QMetaType.Type.Int, data.sslVersion));
        map.put("Host", new QVariant<>(QMetaType.Type.QString, data.host));
        map.put("Port", new QVariant<>(QMetaType.Type.Int, data.port));
        map.put("Password", new QVariant<>(QMetaType.Type.QString, data.password));
        map.put("UseProxy", new QVariant<>(QMetaType.Type.Bool, data.useProxy));
        map.put("ProxyType", new QVariant<>(QMetaType.Type.Int, data.proxyType.id));
        map.put("ProxyHost", new QVariant<>(QMetaType.Type.QString, data.proxyHost));
        map.put("ProxyPort", new QVariant<>(QMetaType.Type.Int, data.proxyPort));
        map.put("ProxyUser", new QVariant<>(QMetaType.Type.QString, data.proxyUser));
        map.put("ProxyPass", new QVariant<>(QMetaType.Type.QString, data.proxyPass));
        return map;
    }

    @NonNull
    @Override
    public NetworkServer fromDatastream(@NonNull Map<String, QVariant> map) {
        return fromLegacy(map);
    }

    @NonNull
    @Override
    public NetworkServer fromLegacy(@NonNull Map<String, QVariant> map) {
        return new NetworkServer(
                (boolean) map.get("UseSSL").data,
                (int) map.get("sslVersion").data,
                (String) map.get("Host").data,
                (int) map.get("Port").data,
                (String) map.get("Password").data,
                (boolean) map.get("UseProxy").data,
                NetworkServer.ProxyType.fromId((int) map.get("ProxyType").data),
                (String) map.get("ProxyHost").data,
                (int) map.get("ProxyPort").data,
                (String) map.get("ProxyUser").data,
                (String) map.get("ProxyPass").data
        );
    }

    @Override
    public NetworkServer from(@NonNull SerializedFunction function) {
        if (function instanceof PackedFunction)
            return fromLegacy(((PackedFunction) function).getData());
        else if (function instanceof UnpackedFunction)
            return fromDatastream(((UnpackedFunction) function).getData());
        else throw new IllegalArgumentException();
    }
}
