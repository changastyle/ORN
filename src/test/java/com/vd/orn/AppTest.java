package com.vd.orn;

import com.vd.orn.util.MasterUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    public AppTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        String entrada = "@javax.persistence.JoinColumn(referencedColumnName=\"\", nullable=true, unique=false, name=\"fkPais\", updatable=true, columnDefinition=\"\", foreignKey=@javax.persistence.ForeignKey(name=\"\", value=PROVIDER_DEFAULT, foreignKeyDefinition=\"\"), table=\"\", insertable=true)):";
        String rta = MasterUtil.substringBetween(entrada, "name=\"", ",");
        System.out.println("SALIDA:" + rta);
//        assertTrue( true );
    }
    public void testApp2()
    {
        ClaseORM claseORM = new ClaseORM("C:\\Users\\ngrossi\\Desktop\\ORMN\\src\\main\\java\\com\\vd\\orn\\MODEL\\Pais.java");
        System.out.println("CLASE:" + claseORM);
        System.out.println("RTA:" + claseORM.dameFKDelMethodoSegunAnnotation("instalacion",true) );;
    }
}
