package cn.com.cis.service;

import cn.com.cis.domain.Layer;
import cn.com.cis.persistence.LayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LayerService {

    @Autowired
    private LayerMapper mapper;

    public void insertLayer(Layer layer) {
        mapper.insertLayer(layer);
    }

    public void deleteLayer(int id) {
        mapper.deleteLayer(id);
    }

    public void updateLayer(Layer layer) {
        mapper.updateLayer(layer);
    }

    @Transactional(readOnly = true)
    public List<Layer> selectAllLayer() {
        return mapper.selectAllLayer();
    }

    @Transactional(readOnly = true)
    public Layer selectLayerById(int id) {
        return mapper.selectLayerById(id);
    }

    public Layer selectLayerBySequence(int sequence){
        return mapper.selectLayerBySequence(sequence);
    }
}
