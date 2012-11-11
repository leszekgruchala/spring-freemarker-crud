<#macro personFormInput bean withCancel=false>
    <#assign id>${bean}.id</#assign>
    <@spring.formHiddenInput path=id/>
    <ul>
        <li>
            <div class="input-prepend">
                <span class="add-on">Name</span>
                <#assign name>${bean}.name</#assign>
                <@spring.formInput path=name attributes="class='input-large' onkeypress='invokeForm(event)'"/>
                <@spring.showErrors separator=", " classOrStyle="error"/>
            </div>
        </li>
        <li>
            <div class="input-prepend">
                <span class="add-on">Birthdate</span>
                <#assign birthDate>${bean}.birthDate</#assign>
                <@spring.formInput path=birthDate attributes="class='input-large' placeholder='dd-mm-yyyy' onkeypress='invokeForm(event)'"/>
                <@spring.showErrors separator=", " classOrStyle="error"/>
            </div>
        </li>
        <li>
            <div class="input-prepend">
                <span class="add-on">@</span>
                <#assign email>${bean}.email</#assign>
                <@spring.formInput path=email attributes="class='input-large' onkeypress='invokeForm(event)'"/>
                <@spring.showErrors separator=", " classOrStyle="error"/>
            </div>
        </li>
    </ul>
    <#if withCancel>
        <input type="button" value="Cancel" class="btn cancel" />
    </#if>
    <input type="submit" value="Submit" class="btn <#if withCancel>submit<#else>submitRight</#if>" />
</#macro>