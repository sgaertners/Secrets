const exampleModal = document.getElementById('exampleModal')
if (exampleModal) {
    exampleModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget
        const id = button.getAttribute('data-bs-whatever')
        const text = button.getAttribute('data-text')
        $('#dbimage').attr("src","/dbimage?id=" +  id +"&kind=KNOWHOW");
        $('#dbtext').html("<pre>" + text + "</pre>");
    })
}