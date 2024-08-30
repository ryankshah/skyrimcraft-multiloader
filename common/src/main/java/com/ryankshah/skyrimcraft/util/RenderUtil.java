package com.ryankshah.skyrimcraft.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class RenderUtil
{
    public static void bind(ResourceLocation loc){
        RenderSystem.setShaderTexture(0,loc);
    }

    public static void blitWithBlend(PoseStack matrices, float x, float y, float texPosX, float texPosY, float width, float height, float texWidth, float texHeight, float zOffset, float alpha){
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        BufferBuilder vertex = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        float u1 = texPosX / (float)texWidth;
        float u2 = (texPosX + width) / (float)texWidth;
        float v1 = texPosY / (float)texHeight;
        float v2 = (texPosY + height) / (float)texHeight;
        Matrix4f m = matrices.last().pose();
//        vertex.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        vertex.addVertex(m,x,y,zOffset).setColor(1,1,1,alpha).setUv(u1,v1);
        vertex.addVertex(m,x,y + height,zOffset).setColor(1,1,1,alpha).setUv(u1,v2);
        vertex.addVertex(m,x + width,y + height,zOffset).setColor(1,1,1,alpha).setUv(u2,v2);
        vertex.addVertex(m,x + width,y,zOffset).setColor(1,1,1,alpha).setUv(u2,v1);

        BufferUploader.drawWithShader(vertex.buildOrThrow());
        RenderSystem.disableBlend();
    }

    public static boolean isMouseInBorders(int mx, int my, int x1, int y1, int x2, int y2){
        return (mx >= x1 && mx <= x2) && (my >= y1 && my <= y2);
    }

    public static void fill(PoseStack matrices,double x1,double y1,double x2,double y2,float r,float g,float b,float a){
        if (x1 > x2){
            double k = x1;
            x1 = x2;
            x2 = k;
        }
        if (y1 > y2){
            double k = y1;
            y1 = y2;
            y2 = k;
        }
        matrices.pushPose();
        Matrix4f matrix4f = matrices.last().pose();
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.addVertex(matrix4f,(float)x1,(float)y1,0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x1,(float)y2,0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x2,(float)y2,0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x2,(float)y1,0).setColor(r,g,b,a);

        BufferUploader.drawWithShader(builder.buildOrThrow());
        RenderSystem.disableBlend();
        matrices.popPose();
    }

    public static void gradientBarHorizontal(PoseStack matrices,double x1,double y1,double x2,double y2,float r,float g,float b,float a){
        if (x1 > x2){
            double k = x1;
            x1 = x2;
            x2 = k;
        }
        if (y1 > y2){
            double k = y1;
            y1 = y2;
            y2 = k;
        }
        Matrix4f matrix4f = matrices.last().pose();
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        double sizeY = y2 - y1;

        RenderSystem.enableBlend();
//        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.addVertex(matrix4f,(float)x1,(float)y1,0).setColor(r,g,b,0);
        builder.addVertex(matrix4f,(float)x1,(float)(y2 - sizeY/2f),0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x2,(float)(y2 - sizeY/2f),0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x2,(float)y1,0).setColor(r,g,b,0);

        builder.addVertex(matrix4f,(float)x1,(float)(y1 + sizeY/2),0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x1,(float)y2 ,0).setColor(r,g,b,0);
        builder.addVertex(matrix4f,(float)x2,(float)y2 ,0).setColor(r,g,b,0);
        builder.addVertex(matrix4f,(float)x2,(float)(y1 + sizeY/2),0).setColor(r,g,b,a);


        BufferUploader.drawWithShader(builder.buildOrThrow());
//        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void gradientBarVertical(PoseStack matrices,double x1,double y1,double x2,double y2,float r,float g,float b,float a){
        if (x1 > x2){
            double k = x1;
            x1 = x2;
            x2 = k;
        }
        if (y1 > y2){
            double k = y1;
            y1 = y2;
            y2 = k;
        }
        Matrix4f matrix4f = matrices.last().pose();
        BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        double sizeX = x2 - x1;
        RenderSystem.enableBlend();
//        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_COLOR);

        builder.addVertex(matrix4f,(float)x1,(float)y1,0).setColor(r,g,b,0);
        builder.addVertex(matrix4f,(float)x1,(float)y2,0).setColor(r,g,b,0);
        builder.addVertex(matrix4f,(float)(x2 - sizeX/2f),(float)y2,0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)(x2 - sizeX/2f),(float)y1,0).setColor(r,g,b,a);

        builder.addVertex(matrix4f,(float)(x1 + sizeX/2f),(float)y1,0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)(x1 + sizeX/2f),(float)y2,0).setColor(r,g,b,a);
        builder.addVertex(matrix4f,(float)x2 ,(float)y2,0).setColor(r,g,b,0);
        builder.addVertex(matrix4f,(float)x2 ,(float)y1,0).setColor(r,g,b,0);

        BufferUploader.drawWithShader(builder.buildOrThrow());
//        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

}