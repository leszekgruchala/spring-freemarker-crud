$(function () {
    $(".icon-edit").click(function () {
        if (currentlyEditedPerson() == getChosenPersonId(this) && $("#editablePersonFrom").length) {
            return false;
        }
        editPerson(this);
    });
    $(".icon-remove").click(function () {
        removePerson(getChosenPersonId(this))
    });

    $(".cancel").click(function () {
        removeEditablePerson();
    });

    $('.personId').each(function (index) {
        if (currentlyEditedPerson() == $(this).val()) {
            $(this).parent().children(".icon-edit").click();
            return false;
        }
    });
});

function invokeForm(e) {
    if (e.keyCode == 13) {
        $(e.target).parent().submit();
//        var form = document.getElementById("mainForm");
//        form.submit();
    }
}

function editPerson(source) {
    removeEditablePerson();

    var newForm = $("#editPersonFrom").clone(true);
    newForm.removeAttr('id').attr('id', "editablePersonFrom").removeClass('hide');
    $(source).parent().parent().append(newForm);

    if (currentlyEditedPerson() == '' || currentlyEditedPerson() != getChosenPersonId(source)) {
        $("#editablePersonFrom .error").remove();
        $("#editablePersonFrom input[name$=id]").val(getChosenPersonId(source));
        $("#editablePersonFrom input[name$=name]").val(getChosenPersonName(source));
        $("#editablePersonFrom input[name$=birthDate]").val(getChosenPersonBirthDate(source));
        $("#editablePersonFrom input[name$=email]").val(getChosenPersonEmail(source));
    }

    setCurrentlyEditedPerson(getChosenPersonId(source));

    $(source).parent().next(".editPerson").show("slow");
}

function removePerson(selectedId) {
    var form = document.forms["removePerson"];
    form.action = form.action + selectedId;
    form.submit();
}

function currentlyEditedPerson() {
    return $("#currentlyEditedPerson").val();
}

function setCurrentlyEditedPerson(value) {
    $("#currentlyEditedPerson").val(value);
}

function getChosenPersonId(elem) {
    return $(elem).parent().children(".personId").val()
}

function getChosenPersonName(elem) {
    return $(elem).parent().children(".personName").val()
}

function getChosenPersonBirthDate(elem) {
    return $(elem).parent().children(".personBirthDate").val()
}

function getChosenPersonEmail(elem) {
    return $(elem).parent().children(".personEmail").val()
}

function removeEditablePerson() {
    $("#editablePersonFrom").hide("slow").remove();
}