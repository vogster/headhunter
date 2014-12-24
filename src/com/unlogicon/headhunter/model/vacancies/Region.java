package com.unlogicon.headhunter.model.vacancies;

/**
 * Created by nik on 07.10.14.
 */

public class Region
{
    private Region[] areas;
    private String id;
    private String name;
    private String parent_id;

    public Region[] getAreas()
    {
        return this.areas;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getParent()
    {
        return this.parent_id;
    }

    public void setAreas(Region[] paramArrayOfRegion)
    {
        this.areas = paramArrayOfRegion;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setParent(String paramString)
    {
        this.parent_id = paramString;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

}
