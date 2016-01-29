package de.kuschku.libquassel.syncables.types;

import android.net.*;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.kuschku.libquassel.BusProvider;
import de.kuschku.libquassel.Client;
import de.kuschku.libquassel.functions.types.InitDataFunction;
import de.kuschku.libquassel.primitives.types.QVariant;
import de.kuschku.libquassel.syncables.serializers.BufferSyncerSerializer;
import de.kuschku.libquassel.syncables.serializers.BufferViewConfigSerializer;
import de.kuschku.util.AndroidAssert;
import de.kuschku.util.observables.callbacks.ElementCallback;
import de.kuschku.util.observables.lists.IObservableList;
import de.kuschku.util.observables.lists.ObservableElementList;

import static de.kuschku.util.AndroidAssert.*;

public class BufferViewConfig extends SyncableObject<BufferViewConfig> {
    private String bufferViewName;
    private List<Integer> TemporarilyRemovedBuffers;
    private boolean hideInactiveNetworks;
    private IObservableList<ElementCallback<Integer>, Integer> BufferList;
    private IObservableList<ElementCallback<Integer>, Integer> NetworkList = new ObservableElementList<>();
    private int allowedBufferTypes;
    private boolean sortAlphabetically;
    private boolean disableDecoration;
    private boolean addNewBuffersAutomatically;
    private int networkId;
    private int minimumActivity;
    private boolean hideInactiveBuffers;
    private List<Integer> RemovedBuffers;
    private Client client;

    public BufferViewConfig(String bufferViewName, List<Integer> temporarilyRemovedBuffers, boolean hideInactiveNetworks, @NonNull List<Integer> bufferList, int allowedBufferTypes, boolean sortAlphabetically, boolean disableDecoration, boolean addNewBuffersAutomatically, int networkId, int minimumActivity, boolean hideInactiveBuffers, List<Integer> removedBuffers) {
        this.bufferViewName = bufferViewName;
        this.TemporarilyRemovedBuffers = temporarilyRemovedBuffers;
        this.hideInactiveNetworks = hideInactiveNetworks;
        this.BufferList = new ObservableElementList<>(bufferList);
        this.allowedBufferTypes = allowedBufferTypes;
        this.sortAlphabetically = sortAlphabetically;
        this.disableDecoration = disableDecoration;
        this.addNewBuffersAutomatically = addNewBuffersAutomatically;
        this.networkId = networkId;
        this.minimumActivity = minimumActivity;
        this.hideInactiveBuffers = hideInactiveBuffers;
        this.RemovedBuffers = removedBuffers;
    }

    public String getBufferViewName() {
        return bufferViewName;
    }

    public void setBufferViewName(String bufferViewName) {
        this.bufferViewName = bufferViewName;
    }

    public List<Integer> getTemporarilyRemovedBuffers() {
        return TemporarilyRemovedBuffers;
    }

    public void setTemporarilyRemovedBuffers(List<Integer> temporarilyRemovedBuffers) {
        TemporarilyRemovedBuffers = temporarilyRemovedBuffers;
    }

    public boolean isHideInactiveNetworks() {
        return hideInactiveNetworks;
    }

    public void setHideInactiveNetworks(boolean hideInactiveNetworks) {
        this.hideInactiveNetworks = hideInactiveNetworks;
    }

    public IObservableList<ElementCallback<Integer>, Integer> getBufferList() {
        return BufferList;
    }

    public void setBufferList(IObservableList<ElementCallback<Integer>, Integer> bufferList) {
        BufferList = bufferList;
    }

    public void setBufferList(@NonNull List<Integer> bufferList) {
        BufferList = new ObservableElementList<>(bufferList);
    }

    public int getAllowedBufferTypes() {
        return allowedBufferTypes;
    }

    public void setAllowedBufferTypes(int allowedBufferTypes) {
        this.allowedBufferTypes = allowedBufferTypes;
    }

    public boolean isSortAlphabetically() {
        return sortAlphabetically;
    }

    public void setSortAlphabetically(boolean sortAlphabetically) {
        this.sortAlphabetically = sortAlphabetically;
    }

    public boolean isDisableDecoration() {
        return disableDecoration;
    }

    public void setDisableDecoration(boolean disableDecoration) {
        this.disableDecoration = disableDecoration;
    }

    public boolean isAddNewBuffersAutomatically() {
        return addNewBuffersAutomatically;
    }

    public void setAddNewBuffersAutomatically(boolean addNewBuffersAutomatically) {
        this.addNewBuffersAutomatically = addNewBuffersAutomatically;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
        if (this.networkId != -1) {
            this.NetworkList.addAll(client.getNetworks());
        } else {
            this.NetworkList.retainAll(Collections.singletonList(networkId));
        }
    }

    public int getMinimumActivity() {
        return minimumActivity;
    }

    public void setMinimumActivity(int minimumActivity) {
        this.minimumActivity = minimumActivity;
    }

    public boolean isHideInactiveBuffers() {
        return hideInactiveBuffers;
    }

    public void setHideInactiveBuffers(boolean hideInactiveBuffers) {
        this.hideInactiveBuffers = hideInactiveBuffers;
    }

    public List<Integer> getRemovedBuffers() {
        return RemovedBuffers;
    }

    public void setRemovedBuffers(List<Integer> removedBuffers) {
        RemovedBuffers = removedBuffers;
    }

    @NonNull
    @Override
    public String toString() {
        return "BufferViewConfig{" +
                "bufferViewName='" + bufferViewName + '\'' +
                ", TemporarilyRemovedBuffers=" + TemporarilyRemovedBuffers +
                ", hideInactiveNetworks=" + hideInactiveNetworks +
                ", BufferList=" + BufferList +
                ", allowedBufferTypes=" + allowedBufferTypes +
                ", sortAlphabetically=" + sortAlphabetically +
                ", disableDecoration=" + disableDecoration +
                ", addNewBuffersAutomatically=" + addNewBuffersAutomatically +
                ", networkId=" + networkId +
                ", minimumActivity=" + minimumActivity +
                ", hideInactiveBuffers=" + hideInactiveBuffers +
                ", RemovedBuffers=" + RemovedBuffers +
                '}';
    }

    @Override
    public void init(@NonNull InitDataFunction function, @NonNull BusProvider provider, @NonNull Client client) {
        this.client = client;
        setObjectName(function.objectName);
        assertNotNull(client.getBufferViewManager());
        client.getBufferViewManager().BufferViews.put(Integer.valueOf(function.objectName), this);
    }

    @Override
    public void update(BufferViewConfig from) {
        this.bufferViewName = from.bufferViewName;
        this.TemporarilyRemovedBuffers = from.TemporarilyRemovedBuffers;
        this.hideInactiveNetworks = from.hideInactiveNetworks;
        this.BufferList = from.BufferList;
        this.allowedBufferTypes = from.allowedBufferTypes;
        this.sortAlphabetically = from.sortAlphabetically;
        this.disableDecoration = from.disableDecoration;
        this.addNewBuffersAutomatically = from.addNewBuffersAutomatically;
        this.networkId = from.networkId;
        this.minimumActivity = from.minimumActivity;
        this.hideInactiveBuffers = from.hideInactiveBuffers;
        this.RemovedBuffers = from.RemovedBuffers;
    }

    @Override
    public void update(Map<String, QVariant> from) {
        update(BufferViewConfigSerializer.get().fromDatastream(from));
    }

    public void addBuffer(int bufferId, int position) {
        BufferList.add(position, bufferId);
    }

    public void SYNC_addBuffer(int bufferId, int position) {
        addBuffer(bufferId, position);
        sync("addBuffer", new Object[]{bufferId, position});
    }

    public void removeBuffer(int bufferId) {
        if (BufferList.contains(bufferId)) BufferList.remove(BufferList.indexOf(bufferId));
    }

    public void SYNC_removeBuffer(int bufferId) {
        removeBuffer(bufferId);
        sync("removeBuffer", new Object[]{bufferId});
    }

    @NonNull
    public IObservableList<ElementCallback<Integer>, Integer> getNetworkList() {
        return NetworkList;
    }

    public void doLateInit() {
        NetworkList.clear();
        // This should initialize the network list
        setNetworkId(getNetworkId());
    }
}