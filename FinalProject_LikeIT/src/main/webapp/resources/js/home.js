$(document).ready(function () {
    $('#delete').on('show.bs.modal', function (e) {
        $section = $(e.relatedTarget).attr('data-section');
        $(this).find('.modal-footer #delete-section').attr('data-section', $section);
    });

    $('#delete').find('.modal-footer #delete-section').on('click', function(){
        document.forms['form-delete-' + $(this).attr('data-section')].submit();
    });

    $('#restore').on('show.bs.modal', function (e) {
        $sectionId = $(e.relatedTarget).attr('data-section');
        $(this).find('.modal-footer #restore-section').attr('data-section', $sectionId);
    });

    $('#restore').find('.modal-footer #restore-section').on('click', function(){
        document.forms['form-restore-' + $(this).attr('data-section')].submit();
    });

    $('[data-tool="tooltip"]').tooltip();
});