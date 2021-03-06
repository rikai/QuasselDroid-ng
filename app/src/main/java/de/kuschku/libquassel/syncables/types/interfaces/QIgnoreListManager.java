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

package de.kuschku.libquassel.syncables.types.interfaces;

import android.support.annotation.NonNull;

import java.util.Map;

import de.kuschku.libquassel.message.Message;
import de.kuschku.libquassel.primitives.types.QVariant;
import de.kuschku.libquassel.syncables.Synced;
import de.kuschku.libquassel.syncables.types.impl.IgnoreListManager;
import de.kuschku.util.observables.lists.ObservableSortedList;

public interface QIgnoreListManager extends QObservable<QIgnoreListManager> {
    @Synced
    void removeIgnoreListItem(final String ignoreRule);

    void _removeIgnoreListItem(final String ignoreRule);

    @Synced
    void toggleIgnoreRule(final String ignoreRule);

    void _toggleIgnoreRule(final String ignoreRule);

    @Synced
    void addIgnoreListItem(IgnoreType type, final String ignoreRule, boolean isRegEx, StrictnessType strictness, ScopeType scope, final String scopeRule, boolean isActive);

    @Synced
    void addIgnoreListItem(int type, final String ignoreRule, boolean isRegEx, int strictness, int scope, final String scopeRule, boolean isActive);

    void _addIgnoreListItem(int type, final String ignoreRule, boolean isRegEx, int strictness, int scope, final String scopeRule, boolean isActive);

    void _addIgnoreListItem(IgnoreListManager.IgnoreListItem item);

    StrictnessType match(String msgContents, String msgSender, Message.Type msgType, String network, String bufferName);

    boolean matches(Message message, QNetwork network);

    void _update(Map<String, QVariant> from);

    void _update(QIgnoreListManager from);

    void requestUpdate(Map<String, QVariant<Object>> variantMap);

    void requestUpdate();

    ObservableSortedList<? extends IgnoreListManager.IgnoreListItem> ignoreList();

    void _toggleIgnoreRule(IgnoreListManager.IgnoreListItem ignoreRule, boolean active);

    enum IgnoreType {
        SenderIgnore(0),
        MessageIgnore(1),
        CtcpIgnore(2);

        public final int value;

        IgnoreType(int value) {
            this.value = value;
        }

        @NonNull
        public static IgnoreType of(int id) {
            switch (id) {
                case 1:
                    return MessageIgnore;
                case 2:
                    return CtcpIgnore;
                default:
                case 0:
                    return SenderIgnore;
            }
        }
    }

    enum StrictnessType {
        UnmatchedStrictness(0),
        SoftStrictness(1),
        HardStrictness(2);

        public final int value;

        StrictnessType(int value) {
            this.value = value;
        }

        @NonNull
        public static StrictnessType of(int id) {
            switch (id) {
                default:
                case 0:
                    return UnmatchedStrictness;
                case 1:
                    return SoftStrictness;
                case 2:
                    return HardStrictness;
            }
        }
    }

    enum ScopeType {
        GlobalScope(0),
        NetworkScope(1),
        ChannelScope(2);

        public final int value;

        ScopeType(int value) {
            this.value = value;
        }

        @NonNull
        public static ScopeType of(int id) {
            switch (id) {
                case 1:
                    return NetworkScope;
                case 2:
                    return ChannelScope;
                default:
                case 0:
                    return GlobalScope;
            }
        }
    }
}
