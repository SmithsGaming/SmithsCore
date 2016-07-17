package com.smithsmodding.smithscore.client.model.data;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Author Orion (Created on: 17.07.2016)
 * Replacement version of the old OBJ System in MC Forge used until the ModelGroup hiding works.
 */
public class SmithsCoreOBJCustomData {
    public boolean ambientOcclusion = true;
    public boolean gui3d = true;
    // should be an enum, TODO
    //public boolean modifyUVs = false;
    public boolean flipV = false;

    public SmithsCoreOBJCustomData(SmithsCoreOBJCustomData parent, ImmutableMap<String, String> customData) {
        this.ambientOcclusion = parent.ambientOcclusion;
        this.gui3d = parent.gui3d;
        this.flipV = parent.flipV;
        this.process(customData);
    }

    public SmithsCoreOBJCustomData() {
    }

    public void process(ImmutableMap<String, String> customData) {
        for (Map.Entry<String, String> e : customData.entrySet()) {
            if (e.getKey().equals("ambient"))
                this.ambientOcclusion = Boolean.valueOf(e.getValue());
            else if (e.getKey().equals("gui3d"))
                this.gui3d = Boolean.valueOf(e.getValue());
            /*else if (e.getKey().equals("modifyUVs"))
                this.modifyUVs = Boolean.valueOf(e.getValue());*/
            else if (e.getKey().equals("flip-v"))
                this.flipV = Boolean.valueOf(e.getValue());
        }
    }
}
