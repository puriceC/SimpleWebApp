getSuccessHandler = (btn) => {
    if (btn.value === 'Delete')
        return () => navigateTo('..');
    if (btn.value === 'Create')
        return (retVal) => navigateTo('../' + retVal.id);
    return (e) => alert('Success');
}

getType = (btn) => {
    if (btn.value === 'Delete')
        return 'DELETE';
    if (btn.value === 'Update')
        return 'PUT';
    return 'POST';
}

submitForm = (button) => {
    let form = $(button).parent();
    let type = getType(button);
    let formUrl = form.attr('action');
    let onSuccess = getSuccessHandler(button);
    let formData = form.serializeArray().reduce(
                (data, field) => {
                    data[field.name] = field.value;
                    return data;
                },
                {});
    $.ajax({
      type: type,
      url: formUrl,
      data: JSON.stringify(formData),
      success: onSuccess,
      dataType: "json",
      contentType : "application/json"
    });
}

navigateTo = (path) => {
    let newLocation = location.href;
    if (location.href[location.href.length-1] !== '/')
        newLocation += '/'
    newLocation += path;
    location.href = newLocation;
}