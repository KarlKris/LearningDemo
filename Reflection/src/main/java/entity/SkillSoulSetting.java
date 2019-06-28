package entity;

//import com.xa.common.resource.anno.Id;
//import com.xa.common.resource.anno.Index;
//import com.xa.common.resource.anno.Resource;
//import com.xa.djx.core.battle.model.UnitValue;
//import com.xa.djx.module.cost.model.CostItem;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

/**
 * 炼魂等级
 */
//@Resource("skill")
@Getter
public class SkillSoulSetting {

    public static final String INDEX = "INDEX";

    // 无意义ID
//    @Id
    private String id;

    // 技能ID
    private int skill;

    // 炼魂等级
    private int level;

    // 战斗技能ID
    private String battleSkillId;

    // 技能等级限制
    private int skillLevelLimit;

    // 进阶消耗
    private CostItem[] costItems;

    //属性列表
    private Map<UnitValue, Integer> addValues;

//    @Index(name = INDEX, unique = true)
    public String skillLevel() {
        return toSkillLevel(skill, level);
    }

    public static String toSkillLevel(int skill, int level) {
        return skill + ":" + level;
    }

    @Override
    public String toString() {
        return "SkillSoulSetting{" +
                "id='" + id + '\'' +
                ", skill=" + skill +
                ", level=" + level +
                ", battleSkillId='" + battleSkillId + '\'' +
                ", skillLevelLimit=" + skillLevelLimit +
                ", costItems=" + Arrays.toString(costItems) +
                ", addValues=" + addValues +
                '}';
    }
}
