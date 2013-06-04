var crud = new (function() {
    //assign _root and config private variables
    var _root = this;
    var _cfg = {}

    /*
     INITIALIZE
     */
    this.init = function(opts) {
        _cfg = $.extend(_cfg, opts);
        //bind events on init
        $(document).ready(function() {
            //bind events
            _bindEvents();
        });
    }
    /*
     Bind Events
     */
    var _bindEvents = function() {
        $(".icon-edit").click(function() {
            if (_currentlyEditedPerson() == _getChosenPersonHash(this) && $('#editablePersonFrom').length) {
                return false;
            }
            editPerson(this);
        });
        $('.icon-remove').click(function() {
            _removePerson(_getChosenPersonHash(this))
        });

        $('.cancel').click(function() {
            _removeEditablePerson();
        });

        $('.personHash').each(function() {
            if (_currentlyEditedPerson() == $(this).val()) {
                $(this).parent().find('.icon-edit').click();
                return false;
            }
        });

        $('body').on('click', 'input', function(e){
            if (e.keyCode == 13) {
                $(e.target).parent().submit();
            }
        });
    }
    /*
     Some Private Method (no "this")
     */
    var editPerson = function(source) {
        _removeEditablePerson();

        var newForm = $('#editPersonFrom').clone(true);
        newForm.removeAttr('id').attr('id', "editablePersonFrom").removeClass('hide');
        $(source).parent().parent().append(newForm);

        var currentlyEditedPerson2 = _currentlyEditedPerson();
        var chosenPersonHash = _getChosenPersonHash(source);
        if (currentlyEditedPerson2 == '' || currentlyEditedPerson2 != chosenPersonHash) {
            $('#editablePersonFrom').find('.error').remove();
            $('#editablePersonFrom').find('input[name$=id]').val(chosenPersonHash);
            $('#editablePersonFrom').find('input[name$=name]').val(_getChosenPersonName(source));
            $('#editablePersonFrom').find('input[name$=birthDate]').val(_getChosenPersonBirthDate(source));
            $('#editablePersonFrom').find('input[name$=email]').val(_getChosenPersonEmail(source));
        }
        _setCurrentlyEditedPerson(chosenPersonHash);
        $(source).parent().next(".editPerson").show("slow");
    }

    var _removePerson = function(selectedId) {
        var form = document.forms["removePerson"];
        form.action = form.action + selectedId;
        form.submit();
    }

    var _currentlyEditedPerson = function() {
        return $("#currentlyEditedPerson").val();
    }

    var _setCurrentlyEditedPerson = function(value) {
        $("#currentlyEditedPerson").val(value);
    }

    var _getChosenPersonHash = function(elem) {
        return $(elem).parent().find('.personHash').val()
    }

    var _getChosenPersonName = function(elem) {
        return $(elem).parent().find('.personName').val()
    }

    var _getChosenPersonBirthDate = function(elem) {
        return $(elem).parent().find('.personBirthDate').val()
    }

    var _getChosenPersonEmail = function(elem) {
        return $(elem).parent().find('.personEmail').val()
    }

    var _removeEditablePerson = function() {
        $('#editablePersonFrom').hide("slow").remove();
    }
})();

crud.init();