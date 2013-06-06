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
        var $body = $('body');
        var $modal = $('.modal');
        $(".icon-edit").click(function() {
            copyValuesForChange(this);
            $modal.modal('show');
        });
        $('.icon-remove').click(function() {
            _removePerson(_getChosenPersonHash(this))
        });
        $body.on('click', 'input', function(e){
            if (e.keyCode == 13) {
                $(e.target).parent().submit();
            }
        });

        $modal.on('hidden', function() {
            $('#editWithErrors').val("false")
            _setValuesOnChangeForm("", "", "", "");
        });
        $body.on('click', '.modal .modal-footer .btn-primary', function() {
            var form = document.forms["editPersonFrom"];
            form.submit();
        });
        if ($('#editWithErrors').val() == "true") {
            $modal.modal('show');
        }
    }

    var copyValuesForChange = function(source) {
        var hash = _getChosenPersonHash(source);
        var name = _getChosenPersonName(source);
        var birthday = _getChosenPersonBirthDate(source);
        var email = _getChosenPersonEmail(source);
        _setValuesOnChangeForm(hash, name, birthday, email);
    }

    var _setValuesOnChangeForm = function(hash, name, birthdate, email) {
        var form = $('#editPersonFrom');
        if ($('#editWithErrors').val() != "true") {
            form.find('.error:first').parent().remove();
        }
        form.find('input[name="hash"]').val(hash);
        form.find('input[name="name"]').val(name);
        form.find('input[name="birthDate"]').val(birthdate);
        form.find('input[name="email"]').val(email);
    }

    var _removePerson = function(selectedId) {
        var action="removePerson/" + selectedId;
        $('<form action="'+ action +'" method="POST"></form>').appendTo('body').submit();
    }

    var _getChosenPersonHash = function(elem) {
        return _getPersonDataBy(elem, '.personHash');
    }

    var _getChosenPersonName = function(elem) {
        return _getPersonDataBy(elem, '.personName');
    }

    var _getChosenPersonBirthDate = function(elem) {
        return _getPersonDataBy(elem, '.personBirthDate');
    }

    var _getChosenPersonEmail = function(elem) {
        return _getPersonDataBy(elem, '.personEmail');
    }

    var _getPersonDataBy = function(elem, selector) {
        return $(elem).closest('.person-data').find(selector).val();
    }
})();

crud.init();