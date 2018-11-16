package nl.han.ica.yaeger.module;

import com.google.inject.AbstractModule;

public class YaegerModule extends AbstractModule {
    public YaegerModule(){
        System.out.println("Created a YAegerModule: " + this);
    }
}
