package link1234gamer.fnafmod.common.entity.ai.RpuppetAI;

import link1234gamer.fnafmod.common.entity.EntityRPuppet;
import link1234gamer.fnafmod.common.tileentity.TileEntityCamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class EntityRPuppetAIStare extends EntityAIBase
{
    private EntityLiving theWatcher;
    protected Entity closestEntity;
    private float maxDistanceForPlayer;
    private int lookTime;
    private Class watchedClass;
    
    public EntityRPuppetAIStare(EntityLiving entity, Class entityClass, float maxDist)
    {
        this.theWatcher = entity;
        this.watchedClass = entityClass;
        this.maxDistanceForPlayer = maxDist;
        this.setMutexBits(2);
    }
    
    public boolean shouldExecute()
    {
    	if (this.theWatcher.getAttackTarget() != null)
    	{
    		this.closestEntity = this.theWatcher.getAttackTarget();
    	}

    	if (this.watchedClass == EntityPlayer.class)
    	{
    		this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, (double)this.maxDistanceForPlayer);
    	}
    	else
    	{
    		this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand((double)this.maxDistanceForPlayer, 3.0D, (double)this.maxDistanceForPlayer), this.theWatcher);
    	}
    	
        return true;
    }
    
    public boolean continueExecuting()
    {
    	return true;
    }
    
    public void resetTask()
    {
        this.closestEntity = null;
    }
    
    public void updateTask()
    {
    	if (((EntityRPuppet)theWatcher).isAgressive())
        {
    		int x = (int)theWatcher.posX - 1;
        	int y = (int)theWatcher.posY;
        	int z = (int)theWatcher.posZ;
        	int radius = 5;
        	TileEntityCamera cam = null;
        	
        	for (int i = -radius; i <= radius; ++i)
        	{
        		for (int j = -radius; j <= radius; ++j)
            	{
        			for (int k = -radius; k <= radius; ++k)
        	    	{
        				TileEntity tileentity = theWatcher.worldObj.getTileEntity(x + i, y + j, z + k);
        				
        				if (tileentity != null && cam == null && tileentity instanceof TileEntityCamera)
        				{
        					cam = (TileEntityCamera)tileentity;
        				}
        	    	}
            	}
        	}
        	
        	if (cam != null && (closestEntity == null || theWatcher.getDistance(cam.xCoord, cam.yCoord, cam.zCoord) < theWatcher.getDistanceToEntity(closestEntity)))
        	{
//        		theWatcher.getLookHelper().setLookPosition(cam.xCoord + 0.5F, cam.yCoord - 0.8F, cam.zCoord + 0.5F, 10.0F, (float)this.theWatcher.getVerticalFaceSpeed());
        	}
        	else if (closestEntity != null)
        	{
    			theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight() - 1.4F, this.closestEntity.posZ, 10.0F, (float)this.theWatcher.getVerticalFaceSpeed());
        	}
        }
    }
}