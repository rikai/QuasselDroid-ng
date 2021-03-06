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

import java.util.List;

import de.kuschku.libquassel.syncables.types.SyncableObject;
import de.kuschku.libquassel.syncables.types.interfaces.QBufferViewConfig;
import de.kuschku.libquassel.syncables.types.interfaces.QBufferViewManager;

public abstract class ABufferViewManager extends SyncableObject<QBufferViewManager> implements QBufferViewManager {
    @Override
    public void createBufferView(QBufferViewConfig bufferView) {
        //_addBufferViewConfig(bufferView);
        syncVar("requestCreateBufferView", bufferView);
    }

    @Override
    public void createBufferViews(List<QBufferViewConfig> bufferViews) {
        //for (QBufferViewConfig config : bufferViews)
//            _addBufferViewConfig(config);
        syncVar("requestCreateBufferViews", bufferViews);
    }

    @Override
    public void deleteBufferView(int bufferViewId) {
//        _deleteBufferViewConfig(bufferViewId);
        syncVar("requestDeleteBufferView", bufferViewId);
    }

    @Override
    public void deleteBufferViews(List<Integer> bufferViews) {
//        for (int config : bufferViews)
//            _deleteBufferViewConfig(config);
        syncVar("requestDeleteBufferViews", bufferViews);
    }
}
