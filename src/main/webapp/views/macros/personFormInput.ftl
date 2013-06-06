<#import "/spring.ftl" as spring />
<#macro personFormInput bean withSubmit=false>
    <#assign hash>${bean}.hash</#assign>
    <@spring.formHiddenInput path=hash/>
    <ul>
        <li>
            <div class="row-fluid">
                <div>
                    <div class="input-prepend">
                        <span class="add-on">Name</span>
                        <#assign name>${bean}.name</#assign>
                        <@spring.formInput path=name attributes="class='input-large'"/>
                    </div>
                </div>
                <div>
                    <@spring.showErrors separator="<br>" classOrStyle="error"/>
                </div>
            </div>
        </li>
        <li>
            <div class="row-fluid">
                <div>
                    <div class="input-prepend">
                        <span class="add-on">Birthdate</span>
                        <#assign birthDate>${bean}.birthDate</#assign>
                        <@spring.formInput path=birthDate attributes="class='input-large' placeholder='dd-mm-yyyy'"/>
                    </div>
                </div>
                <div>
                    <@spring.showErrors separator=", " classOrStyle="error"/>
                </div>
            </div>
        </li>
        <li>
            <div class="row-fluid">
                <div>
                    <div class="input-prepend">
                        <span class="add-on">@</span>
                        <#assign email>${bean}.email</#assign>
                        <@spring.formInput path=email attributes="class='input-large'"/>
                    </div>
                </div>
                <div>
                    <@spring.showErrors separator=", " classOrStyle="error"/>
                </div>
            </div>
        </li>
    </ul>
    <#if withSubmit>
        <input type="submit" value="Submit" class="btn submitRight" />
    </#if>
</#macro>