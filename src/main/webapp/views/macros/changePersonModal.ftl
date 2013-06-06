<#include "personFormInput.ftl"/>
<#macro changePersonModal>
<input type="hidden" name="editWithErrors" id="editWithErrors" value="${editWithErrors!"false"}"/>
<div class="modal hide fade" <#if editWithErrors?? && editWithErrors=="true">style="width: 600px; margin-left:-300px;"</#if>>
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>Change person data</h3>
    </div>
    <div class="modal-body">
        <form id="editPersonFrom" class=".form-inline" action="editPerson" method="POST">
            <@personFormInput bean="editablePerson"/>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
    </div>
</div>
</#macro>