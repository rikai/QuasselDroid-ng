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

package de.kuschku.libquassel.syncables.types.abstracts;

import de.kuschku.libquassel.primitives.QMetaType;
import de.kuschku.libquassel.syncables.types.SyncableObject;
import de.kuschku.libquassel.syncables.types.interfaces.QBufferViewConfig;

public abstract class ABufferViewConfig extends SyncableObject<QBufferViewConfig> implements QBufferViewConfig {
    static final String intName = QMetaType.Type.Int.getSerializableName();

    @Override
    public void setNetworkId(int networkId) {
        _setNetworkId(networkId);
        syncVar("setNetworkId", networkId);
    }

    @Override
    public void setAddNewBuffersAutomatically(boolean addNewBuffersAutomatically) {
        _setAddNewBuffersAutomatically(addNewBuffersAutomatically);
        syncVar("setAddNewBuffersAutomatically", addNewBuffersAutomatically);
    }

    @Override
    public void setSortAlphabetically(boolean sortAlphabetically) {
        _setSortAlphabetically(sortAlphabetically);
        syncVar("setSortAlphabetically", sortAlphabetically);
    }

    @Override
    public void setDisableDecoration(boolean disableDecoration) {
        _setDisableDecoration(disableDecoration);
        syncVar("setDisableDecoration", disableDecoration);
    }

    @Override
    public void setAllowedBufferTypes(int bufferTypes) {
        _setAllowedBufferTypes(bufferTypes);
        syncVar("setAllowedBufferTypes", bufferTypes);
    }

    @Override
    public void setMinimumActivity(MinimumActivity activity) {
        _setMinimumActivity(activity);
        syncVar("setMinimumActivity", activity.id);
    }

    @Override
    public void setHideInactiveBuffers(boolean hideInactiveBuffers) {
        _setHideInactiveBuffers(hideInactiveBuffers);
        syncVar("setHideInactiveBuffers", hideInactiveBuffers);
    }

    @Override
    public void setHideInactiveNetworks(boolean hideInactiveNetworks) {
        _setHideInactiveNetworks(hideInactiveNetworks);
        syncVar("setHideInactiveNetworks", hideInactiveNetworks);
    }

    @Override
    public void setBufferViewName(String bufferViewName) {
        _setBufferViewName(bufferViewName);
        requestSetBufferViewName(bufferViewName);
    }

    @Override
    public void requestSetBufferViewName(String bufferViewName) {
        _requestSetBufferViewName(bufferViewName);
        syncVar("requestSetBufferViewName", bufferViewName);

    }

    @Override
    public void addBuffer(int bufferId, int pos) {
        _addBuffer(bufferId, pos);
        requestAddBuffer(bufferId, pos);
    }

    @Override
    public void requestAddBuffer(int bufferId, int pos) {
        _requestAddBuffer(bufferId, pos);
        sync("requestAddBuffer", new String[]{"BufferId", intName}, new Object[]{bufferId, pos});
    }

    @Override
    public void moveBuffer(int bufferId, int pos) {
        _moveBuffer(bufferId, pos);
        requestMoveBuffer(bufferId, pos);
    }

    @Override
    public void requestMoveBuffer(int bufferId, int pos) {
        _requestMoveBuffer(bufferId, pos);
        sync("requestMoveBuffer", new String[]{"BufferId", intName}, new Object[]{bufferId, pos});
    }

    @Override
    public void removeBuffer(int bufferId) {
        _removeBuffer(bufferId);
        requestRemoveBuffer(bufferId);
    }

    @Override
    public void requestRemoveBuffer(int bufferId) {
        _requestRemoveBuffer(bufferId);
        sync("requestRemoveBuffer", new String[]{"BufferId"}, new Object[]{bufferId});
    }

    @Override
    public void removeBufferPermanently(int bufferId) {
        _removeBufferPermanently(bufferId);
        sync("removeBufferPermanently", new String[]{"BufferId"}, new Object[]{bufferId});
    }

    @Override
    public void requestRemoveBufferPermanently(int bufferId) {
        _requestRemoveBufferPermanently(bufferId);
        sync("requestRemoveBufferPermanently", new String[]{"BufferId"}, new Object[]{bufferId});
    }
}
