package moriyashiine.aylyth.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;

@Environment(EnvType.CLIENT)
public class YmpeInfestationModel extends BipedEntityModel<AbstractClientPlayerEntity> {
	private final ModelPart ympe_infestation_01_main;
	
	public YmpeInfestationModel(ModelPart root) {
		super(root, RenderLayer::getArmorCutoutNoCull);
		ympe_infestation_01_main = root.getChild("ympe_infestation_01_main");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = data.getRoot();
		ModelPartData ympe_infestation_01_main = root.addChild("ympe_infestation_01_main", ModelPartBuilder.create(), ModelTransform.of(-6.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		ModelPartData branch01a = ympe_infestation_01_main.addChild("branch01a", ModelPartBuilder.create().uv(88, 0).cuboid(-0.51F, 0.5F, -0.25F, 1.0F, 2.0F, 2.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		branch01a.addChild("cube_r1", ModelPartBuilder.create().uv(97, 0).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		branch01a.addChild("branch01b", ModelPartBuilder.create().uv(95, 7).cuboid(-0.55F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F, -0.1F, -0.1F)), ModelTransform.of(0.0F, -2.25F, -1.5F, 0.0F, 0.0F, 0.0F));
		branch01a.addChild("branch01c", ModelPartBuilder.create().uv(88, 10).cuboid(-0.65F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.1F, -0.1F, -0.1F)), ModelTransform.of(0.25F, -2.25F, -1.5F, 0.9599F, 0.1309F, -0.48F));
		ModelPartData branch01d = branch01a.addChild("branch01d", ModelPartBuilder.create().uv(90, 11).cuboid(-0.15F, -0.25F, -0.5F, 3.0F, 1.0F, 1.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(0.75F, -2.75F, -2.5F, 0.9599F, 0.1309F, -1.1781F));
		branch01d.addChild("branch01dLeaf02_r1", ModelPartBuilder.create().uv(87, 24).cuboid(-1.25F, 0.0F, -0.75F, 1.0F, 0.0F, 1.0F), ModelTransform.of(2.5F, 0.0F, 0.0F, 0.3491F, -0.829F, -0.0873F));
		branch01d.addChild("branch01dLeaf_r1", ModelPartBuilder.create().uv(84, 17).cuboid(0.0F, 0.0F, -1.75F, 2.0F, 0.0F, 2.0F), ModelTransform.of(2.5F, 0.0F, 0.0F, 0.2182F, -0.6981F, -0.3927F));
		ModelPartData branch01Twig01 = branch01a.addChild("branch01Twig01", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.25F, -1.5F, 0.0F, 0.0F, 0.0F));
		branch01Twig01.addChild("branch01Twig01Leaf_r1", ModelPartBuilder.create().uv(87, 24).cuboid(-0.75F, 0.25F, -0.25F, 1.0F, 0.0F, 1.0F), ModelTransform.of(-1.25F, -1.25F, 0.25F, 0.0F, -0.7854F, 0.3491F));
		branch01Twig01.addChild("cube_r2", ModelPartBuilder.create().uv(96, 9).cuboid(-2.0F, -0.5F, -0.25F, 2.0F, 1.0F, 1.0F, new Dilation(-0.25F, -0.25F, -0.25F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		ModelPartData branch02a = ympe_infestation_01_main.addChild("branch02a", ModelPartBuilder.create().uv(97, 10).cuboid(-0.52F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, 0.25F, 0.5F, -0.5672F, 0.0F, 0.0F));
		branch02a.addChild("branch02Twig02_r1", ModelPartBuilder.create().uv(96, 0).cuboid(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.25F, -0.25F, -0.25F)), ModelTransform.of(-0.5F, -1.0F, 0.0F, 0.0F, -0.6109F, 0.5672F));
		branch02a.addChild("branch02Twig01_r1", ModelPartBuilder.create().uv(86, 0).cuboid(-1.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(-0.25F, -0.25F, -0.25F)), ModelTransform.of(-0.5F, -3.0F, 0.0F, 0.0F, 0.0F, 0.7418F));
		ModelPartData branch02b = branch02a.addChild("branch02b", ModelPartBuilder.create().uv(87, 0).cuboid(-1.0F, -1.0F, -0.51F, 3.0F, 1.0F, 1.0F), ModelTransform.of(0.5F, -2.75F, 0.0F, 0.0F, 0.0F, 0.0F));
		branch02b.addChild("branch02c", ModelPartBuilder.create().uv(97, 10).cuboid(-1.0F, -3.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F, -0.1F, -0.1F)), ModelTransform.of(-0.25F, -0.5F, 0.0F, 0.9163F, 0.0F, 0.0F));
		ModelPartData branch02d = branch02b.addChild("branch02d", ModelPartBuilder.create().uv(93, 11).cuboid(-0.5F, -2.75F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(-0.75F, -2.25F, -2.0F, 1.6144F, 0.0F, 0.0F));
		branch02d.addChild("branch02dLeaf_r1", ModelPartBuilder.create().uv(86, 18).cuboid(-0.25F, -1.75F, 0.0F, 0.0F, 2.0F, 2.0F), ModelTransform.of(0.0F, -2.25F, 0.0F, 0.7854F, 0.0F, -0.1745F));
		branch02b.addChild("branch02e", ModelPartBuilder.create().uv(93, 5).cuboid(-1.5F, -1.75F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(0.25F, -2.25F, -2.0F, 0.0436F, 0.0F, 0.0F));
		branch02b.addChild("branch02f", ModelPartBuilder.create().uv(93, 0).cuboid(-0.5F, -2.75F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(-0.1F, -0.1F, -0.1F)), ModelTransform.of(1.75F, -0.25F, 0.0F, 0.6109F, 0.0F, 0.3054F));
		ModelPartData branch02g = branch02b.addChild("branch02g", ModelPartBuilder.create(), ModelTransform.of(2.5F, -2.5F, -1.25F, 0.6109F, 0.0F, 0.9163F));
		branch02g.addChild("cube_r3", ModelPartBuilder.create().uv(91, 0).cuboid(-0.5F, -2.75F, -1.5F, 1.0F, 3.0F, 2.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));
		branch02b.addChild("branch02h", ModelPartBuilder.create().uv(98, 5).cuboid(-0.5F, -1.75F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(2.5F, -2.5F, -1.5F, 0.5236F, 0.0F, -0.2618F));
		ModelPartData branch02i = branch02b.addChild("branch02i", ModelPartBuilder.create().uv(86, 2).cuboid(-0.5F, -1.75F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.25F, -0.25F, -0.25F)), ModelTransform.of(2.0F, -3.75F, -2.0F, 0.5236F, 0.0F, 0.5236F));
		branch02i.addChild("branch02iLeaf_r1", ModelPartBuilder.create().uv(93, 20).cuboid(-2.0F, -2.0F, 0.0F, 2.0F, 2.0F, 0.0F), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.3491F, 0.48F, 0.4363F));
		ModelPartData branch02Twig03 = branch02b.addChild("branch02Twig03", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.0F, 0.5F, 0.0F, 0.0F, 0.0F));
		branch02Twig03.addChild("branch02Twig03Leaf_r1", ModelPartBuilder.create().uv(88, 24).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F), ModelTransform.of(0.0F, -0.75F, 0.25F, -0.3054F, 0.4363F, 0.829F));
		branch02Twig03.addChild("cube_r4", ModelPartBuilder.create().uv(98, 0).cuboid(-0.5F, -1.25F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.3F, -0.3F, -0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
		ModelPartData branch03a = ympe_infestation_01_main.addChild("branch03a", ModelPartBuilder.create().uv(87, 12).cuboid(-0.5F, -0.5F, 0.0F, 2.0F, 1.0F, 1.0F), ModelTransform.of(0.0F, 1.0F, 1.5F, 0.7854F, 0.0F, 0.0F));
		ModelPartData branch03b = branch03a.addChild("branch03b", ModelPartBuilder.create(), ModelTransform.of(1.25F, 0.25F, 0.75F, 0.7854F, 0.0F, 0.9163F));
		branch03b.addChild("cube_r5", ModelPartBuilder.create().uv(87, 13).cuboid(-0.5F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));
		ModelPartData branch03c = branch03a.addChild("branch03c", ModelPartBuilder.create(), ModelTransform.of(1.75F, -0.5F, 1.75F, 0.7854F, -0.8727F, 2.0071F));
		branch03c.addChild("cube_r6", ModelPartBuilder.create().uv(96, 13).cuboid(-0.5F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new Dilation(-0.3F, -0.3F, -0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));
		ModelPartData branch03d = branch03a.addChild("branch03d", ModelPartBuilder.create(), ModelTransform.of(1.75F, -0.5F, 1.75F, 0.7854F, -1.9635F, 2.9234F));
		branch03d.addChild("cube_r7", ModelPartBuilder.create().uv(88, 13).cuboid(-0.5F, -0.5F, -0.25F, 1.0F, 1.0F, 2.0F, new Dilation(-0.3F, -0.3F, -0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));
		ModelPartData branch04a = ympe_infestation_01_main.addChild("branch04a", ModelPartBuilder.create().uv(86, 8).cuboid(-0.5F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F), ModelTransform.of(0.0F, 1.0F, -1.5F, -0.7854F, 0.0F, 0.0F));
		branch04a.addChild("branch04Twig01_r1", ModelPartBuilder.create().uv(94, 1).cuboid(-2.25F, -0.5F, -0.75F, 2.0F, 1.0F, 1.0F, new Dilation(-0.25F, -0.25F, -0.25F)), ModelTransform.of(0.0F, 0.0F, -0.5F, 0.0F, -1.1345F, 0.48F));
		branch04a.addChild("branch04_r1", ModelPartBuilder.create().uv(90, 4).cuboid(-0.52F, -0.75F, -0.5F, 1.0F, 3.0F, 1.0F), ModelTransform.of(0.0F, -0.25F, 1.75F, -1.4835F, 0.0F, 0.0F));
		ModelPartData branch04b = branch04a.addChild("branch04b", ModelPartBuilder.create(), ModelTransform.of(1.25F, 0.25F, -0.75F, -0.7854F, 0.0F, 0.9163F));
		branch04b.addChild("cube_r8", ModelPartBuilder.create().uv(92, 5).cuboid(-0.5F, -0.5F, -1.75F, 1.0F, 1.0F, 2.0F, new Dilation(-0.2F, -0.2F, -0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));
		ModelPartData branch04c = branch04a.addChild("branch04c", ModelPartBuilder.create(), ModelTransform.of(1.75F, -0.5F, -1.75F, 0.3927F, 0.5672F, 2.0071F));
		branch04c.addChild("cube_r9", ModelPartBuilder.create().uv(93, 4).cuboid(-0.5F, -0.5F, -1.75F, 1.0F, 1.0F, 2.0F, new Dilation(-0.3F, -0.3F, -0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));
		ModelPartData branch04d = branch04a.addChild("branch04d", ModelPartBuilder.create(), ModelTransform.of(1.75F, -0.5F, -1.75F, 1.0472F, 0.2618F, -2.5307F));
		branch04d.addChild("branch04dLeaf_r1", ModelPartBuilder.create().uv(87, 24).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F), ModelTransform.of(-0.25F, 0.0F, -1.25F, -0.2618F, -0.5236F, 0.0436F));
		branch04d.addChild("cube_r10", ModelPartBuilder.create().uv(91, 4).cuboid(-0.5F, -0.5F, -1.75F, 1.0F, 1.0F, 2.0F, new Dilation(-0.3F, -0.3F, -0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));
		return TexturedModelData.of(data, 128, 128);
	}
}
