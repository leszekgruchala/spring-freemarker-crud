$(function () {
    $(".icon-edit").click(function () {
        if (currentlyEditedPerson() == getChosenPersonHash(this) && $("#editablePersonFrom").length) {
            return false;
        }
        editPerson(this);
    });
    $(".icon-remove").click(function () {
        removePerson(getChosenPersonHash(this))
    });

    $(".cancel").click(function () {
        removeEditablePerson();
    });

    $('.personHash').each(function (index) {
        if (currentlyEditedPerson() == $(this).val()) {
            $(this).parent().children(".icon-edit").click();
            return false;
        }
    });
});

function invokeForm(e) {
    if (e.keyCode == 13) {
        $(e.target).parent().submit();
    }
}

function editPerson(source) {
    removeEditablePerson();

    var newForm = $("#editPersonFrom").clone(true);
    newForm.removeAttr('id').attr('id', "editablePersonFrom").removeClass('hide');
    $(source).parent().parent().append(newForm);

    var currentlyEditedPerson2 = currentlyEditedPerson();
    var chosenPersonHash = getChosenPersonHash(source);
    if (currentlyEditedPerson2 == '' || currentlyEditedPerson2 != chosenPersonHash) {
        $("#editablePersonFrom .error").remove();
        $("#editablePersonFrom input[name$=id]").val(chosenPersonHash);
        $("#editablePersonFrom input[name$=name]").val(getChosenPersonName(source));
        $("#editablePersonFrom input[name$=birthDate]").val(getChosenPersonBirthDate(source));
        $("#editablePersonFrom input[name$=email]").val(getChosenPersonEmail(source));
    }

    setCurrentlyEditedPerson(chosenPersonHash);

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

function getChosenPersonHash(elem) {
    return $(elem).parent().children(".personHash").val()
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