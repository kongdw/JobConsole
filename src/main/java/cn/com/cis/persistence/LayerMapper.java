package cn.com.cis.persistence;


import cn.com.cis.domain.Layer;

import java.util.List;

public interface LayerMapper {

    void insertLayer(Layer layer);

    void deleteLayer(int id);

    void updateLayer(Layer layer);

    List<Layer> selectAllLayer();

    Layer selectLayerById(int id);

    Layer selectLayerBySequence(int sequence);

}
