package cn.com.cis.web.actions;

import cn.com.cis.domain.JobEntity;
import cn.com.cis.domain.Layer;
import cn.com.cis.service.JobEntityService;
import cn.com.cis.service.LayerService;
import cn.com.cis.web.bean.LayerActionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

@Controller
@RequestMapping(value = "/layer")
public class LayerController {

    @Autowired
    private LayerService layerService;

    @Autowired
    private JobEntityService jobEntityService;


    @ModelAttribute("layerActionBean")
    public LayerActionBean createFormBean() {
        return new LayerActionBean();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView mav = new ModelAndView("layer/add");
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView save(LayerActionBean bean) {
        ModelAndView mav = new ModelAndView("redirect:/layer/list");
        Layer layer = new Layer();
        layer.setName(bean.getName());
        layer.setSequence(bean.getSequence());
        Layer tmp = layerService.selectLayerBySequence(bean.getSequence());
        if (tmp == null) {
            layerService.insertLayer(layer);
        } else {
            mav = new ModelAndView("layer/add");
            mav.addObject("message", "已经存在该顺序号，请检查!");
            mav.addObject("layer", layer);
            return mav;
        }

        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editView(int id) {
        ModelAndView mav = new ModelAndView("layer/edit");
        Layer layer = layerService.selectLayerById(id);
        mav.addObject("layer", layer);
        return mav;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView update(LayerActionBean bean) {
        ModelAndView mav = new ModelAndView("redirect:/layer/list");
        Layer layer = layerService.selectLayerById(bean.getId());
        layer.setName(bean.getName());
        layer.setSequence(bean.getSequence());
        layerService.updateLayer(layer);
        return mav;
    }


    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ModelAndView del(int id, RedirectAttributesModelMap modelMap) {
        ModelAndView mav = new ModelAndView("redirect:/layer/list");
        Layer layer = layerService.selectLayerById(id);
        if (layer != null) {
            List<JobEntity> jobs = jobEntityService.selectJobEntityByLayer(id, true);
            if (jobs != null && jobs.size() > 0) {
                modelMap.addFlashAttribute("error", "该层级下已经存在作业，不能删除！");
            }else {
                layerService.deleteLayer(id);
            }
        }
        return mav;
    }

    @RequestMapping(value = "/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("layer/list");
        List<Layer> layerList = layerService.selectAllLayer();
        mav.addObject("layerList", layerList);
        return mav;
    }


}
