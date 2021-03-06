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

package de.kuschku.util.observables.callbacks.wrappers;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import de.kuschku.util.observables.callbacks.UICallback;
import de.kuschku.util.observables.callbacks.UIParentCallback;

@UiThread
public class ParentUICallbackWrapper implements UICallback {
    @NonNull
    private final UIParentCallback wrapped;

    public ParentUICallbackWrapper(@NonNull UIParentCallback wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void notifyItemInserted(int position) {
        wrapped.notifyParentItemInserted(position);
    }

    @Override
    public void notifyItemChanged(int position) {
        wrapped.notifyParentItemChanged(position);
    }

    @Override
    public void notifyItemRemoved(int position) {
        wrapped.notifyParentItemRemoved(position);
    }

    @Override
    public void notifyItemMoved(int from, int to) {
        notifyItemRemoved(from);
        notifyItemInserted(to);
    }

    @Override
    public void notifyItemRangeInserted(int position, int count) {
        for (int i = position; i < position + count; i++) {
            notifyItemInserted(i);
        }
    }

    @Override
    public void notifyItemRangeChanged(int position, int count) {
        for (int i = position; i < position + count; i++) {
            notifyItemChanged(i);
        }
    }

    @Override
    public void notifyItemRangeRemoved(int position, int count) {
        for (int i = position; i < position + count; i++) {
            notifyItemRemoved(i);
        }
    }
}
