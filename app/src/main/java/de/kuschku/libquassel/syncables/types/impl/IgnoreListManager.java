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

package de.kuschku.libquassel.syncables.types.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import de.kuschku.libquassel.BusProvider;
import de.kuschku.libquassel.client.Client;
import de.kuschku.libquassel.localtypes.BacklogFilter;
import de.kuschku.libquassel.message.Message;
import de.kuschku.libquassel.primitives.types.QVariant;
import de.kuschku.libquassel.syncables.serializers.IgnoreListManagerSerializer;
import de.kuschku.libquassel.syncables.types.abstracts.AIgnoreListManager;
import de.kuschku.libquassel.syncables.types.interfaces.QIgnoreListManager;
import de.kuschku.util.observables.lists.ObservableSortedList;
import de.kuschku.util.regex.SmartRegEx;

import static de.kuschku.util.AndroidAssert.assertEquals;

public class IgnoreListManager extends AIgnoreListManager {
    @NonNull
    private final ObservableSortedList<IgnoreListItem> ignoreList = new ObservableSortedList<>(IgnoreListItem.class, new ObservableSortedList.ItemComparator<IgnoreListItem>() {
        @Override
        public int compare(IgnoreListItem o1, IgnoreListItem o2) {
            return o1.ignoreRule.rule().compareTo(o2.ignoreRule.rule());
        }

        @Override
        public boolean areContentsTheSame(IgnoreListItem oldItem, IgnoreListItem newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(IgnoreListItem item1, IgnoreListItem item2) {
            return item1.ignoreRule.rule().equals(item2.ignoreRule.rule());
        }
    });


    public IgnoreListManager(@NonNull List<Integer> scope, @NonNull List<Integer> ignoreType,
                             @NonNull List<Boolean> isActive, @NonNull List<String> scopeRule, @NonNull List<Boolean> isRegEx,
                             @NonNull List<Integer> strictness, @NonNull List<String> ignoreRule) {
        assertEquals(scope.size(), ignoreType.size(), isActive.size(), scopeRule.size(), isRegEx.size(), strictness.size(), ignoreRule.size());

        for (int i = 0; i < scope.size(); i++) {
            ignoreList.add(new IgnoreListItem(
                    ignoreType.get(i),
                    ignoreRule.get(i),
                    isRegEx.get(i),
                    strictness.get(i),
                    scope.get(i),
                    scopeRule.get(i),
                    isActive.get(i)
            ));
        }
    }

    @Override
    public void _removeIgnoreListItem(String ignoreRule) {
        ignoreList.remove(indexOf(ignoreRule));
        _update();
    }

    private int indexOf(String ignoreRule) {
        for (int i = 0; i < ignoreList.size(); i++) {
            if (ignoreList.get(i).ignoreRule.rule().equals(ignoreRule))
                return i;
        }
        return -1;
    }

    @Override
    public void _toggleIgnoreRule(String ignoreRule) {
        IgnoreListItem item = ignoreList.get(indexOf(ignoreRule));
        item.isActive = !item.isActive;
        _update();
    }

    @Override
    public void _addIgnoreListItem(int type, @NonNull String ignoreRule, boolean isRegEx, int strictness, int scope, @NonNull String scopeRule, boolean isActive) {
        if (contains(ignoreRule))
            return;

        ignoreList.add(new IgnoreListItem(type, ignoreRule, isRegEx, strictness, scope, scopeRule, isActive));
        _update();
    }

    @Override
    public void _addIgnoreListItem(IgnoreListItem item) {
        if (contains(item.ignoreRule.rule()))
            return;

        ignoreList.add(item);
        _update();
    }

    private boolean contains(String ignoreRule) {
        return indexOf(ignoreRule) != -1;
    }

    @Override
    public StrictnessType match(String msgContents, String msgSender, Message.Type msgType, @NonNull String network, @NonNull String bufferName) {
        if (msgType == Message.Type.Plain || msgType == Message.Type.Notice || msgType == Message.Type.Action) {
            for (IgnoreListItem item : ignoreList) {
                if (!item.isActive || item.type == IgnoreType.CtcpIgnore)
                    continue;

                if (item.scopeMatch(network, bufferName)) {
                    String str;
                    if (item.type == IgnoreType.MessageIgnore)
                        str = msgContents;
                    else
                        str = msgSender;

                    if (item.matches(str))
                        return item.strictness;
                }
            }
        }
        return StrictnessType.UnmatchedStrictness;
    }

    @Override
    public void _update(@NonNull Map<String, QVariant> from) {
        _update(IgnoreListManagerSerializer.get().fromDatastream(from));
    }

    @Override
    public void _update(QIgnoreListManager from) {
        this.ignoreList.retainAll(from.ignoreList());
        this.ignoreList.addAll(from.ignoreList());
        this._update();
    }

    @Override
    public void _update() {
        super._update();
        synchronized (client.backlogStorage().getFilters()) {
            for (BacklogFilter filter : client.backlogStorage().getFilters()) {
                filter.update();
            }
        }
    }

    @Override
    public void init(@NonNull String objectName, @NonNull BusProvider provider, @NonNull Client client) {
        super.init(objectName, provider, client);
        client.setIgnoreListManager(this);
    }

    @Override
    public String toString() {
        return String.valueOf(ignoreList);
    }

    @Override
    public void requestUpdate() {
        requestUpdate(IgnoreListManagerSerializer.get().toVariantMap(this));
    }

    @Override
    public ObservableSortedList<? extends IgnoreListItem> ignoreList() {
        return ignoreList;
    }

    @Override
    public void _toggleIgnoreRule(IgnoreListItem ignoreRule, boolean active) {
        ignoreRule.isActive = active;
    }

    public static class IgnoreListItem {
        private final IgnoreType type;
        @NonNull
        private final SmartRegEx ignoreRule;
        private final boolean isRegEx;
        private final StrictnessType strictness;
        private final ScopeType scope;
        @NonNull
        private final SmartRegEx[] scopeRules;
        private final String scopeRule;
        private boolean isActive;

        public IgnoreListItem(int type, @Nullable String ignoreRule, boolean isRegEx, int strictness, int scope, @Nullable String scopeRule, boolean isActive) {
            this(IgnoreType.of(type), ignoreRule, isRegEx, StrictnessType.of(strictness), ScopeType.of(scope), scopeRule, isActive);
        }

        public IgnoreListItem(IgnoreType type, @Nullable String ignoreRule, boolean isRegEx, StrictnessType strictness, ScopeType scope, @Nullable String scopeRule, boolean isActive) {
            if (scopeRule == null)
                scopeRule = "";
            if (ignoreRule == null)
                ignoreRule = "";

            this.type = type;
            this.ignoreRule = new SmartRegEx(ignoreRule, Pattern.CASE_INSENSITIVE, SmartRegEx.Syntax.WILDCARD);
            this.isRegEx = isRegEx;
            this.strictness = strictness;
            this.scope = scope;
            this.isActive = isActive;

            String[] scopeRules = scopeRule.split(";");
            this.scopeRule = scopeRule;
            this.scopeRules = new SmartRegEx[scopeRules.length];
            for (int i = 0; i < scopeRules.length; i++) {
                this.scopeRules[i] = new SmartRegEx(scopeRules[i].trim(), Pattern.CASE_INSENSITIVE, SmartRegEx.Syntax.WILDCARD);
            }
        }

        public IgnoreType getType() {
            return type;
        }

        @NonNull
        public SmartRegEx getIgnoreRule() {
            return ignoreRule;
        }

        public boolean isRegEx() {
            return isRegEx;
        }

        public StrictnessType getStrictness() {
            return strictness;
        }

        public ScopeType getScope() {
            return scope;
        }

        @NonNull
        public String getScopeRule() {
            return scopeRule;
        }

        public boolean isActive() {
            return isActive;
        }

        public boolean matches(@NonNull String text) {
            return ignoreRule.matches(text, !isRegEx);
        }

        private boolean scopeMatch(@NonNull String network, @NonNull String bufferName) {
            switch (scope) {
                case NetworkScope:
                    return scopeMatch(network);
                case ChannelScope:
                    return scopeMatch(bufferName);
                default:
                case GlobalScope:
                    return true;
            }
        }

        private boolean scopeMatch(@NonNull String scope) {
            for (SmartRegEx scopeRule : scopeRules) {
                if (scopeRule.matches(scope, !isRegEx))
                    return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "IgnoreListItem{" +
                    "type=" + type +
                    ", ignoreRule=" + ignoreRule +
                    ", isRegEx=" + isRegEx +
                    ", strictness=" + strictness +
                    ", scope=" + scope +
                    ", scopeRules=" + Arrays.toString(scopeRules) +
                    ", isActive=" + isActive +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof IgnoreListItem)) return false;

            IgnoreListItem item = (IgnoreListItem) o;

            if (isRegEx() != item.isRegEx()) return false;
            if (isActive() != item.isActive()) return false;
            if (getType() != item.getType()) return false;
            if (!getIgnoreRule().equals(item.getIgnoreRule())) return false;
            if (getStrictness() != item.getStrictness()) return false;
            if (getScope() != item.getScope()) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(scopeRules, item.scopeRules)) return false;
            return getScopeRule() != null ? getScopeRule().equals(item.getScopeRule()) : item.getScopeRule() == null;

        }

        @Override
        public int hashCode() {
            int result = getType() != null ? getType().hashCode() : 0;
            result = 31 * result + getIgnoreRule().hashCode();
            result = 31 * result + (isRegEx() ? 1 : 0);
            result = 31 * result + (getStrictness() != null ? getStrictness().hashCode() : 0);
            result = 31 * result + (getScope() != null ? getScope().hashCode() : 0);
            result = 31 * result + Arrays.hashCode(scopeRules);
            result = 31 * result + (getScopeRule() != null ? getScopeRule().hashCode() : 0);
            result = 31 * result + (isActive() ? 1 : 0);
            return result;
        }
    }
}
