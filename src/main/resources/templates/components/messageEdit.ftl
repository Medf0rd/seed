<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">

        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add new post</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
            </div>
            <form method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">Message:</label>
                        <textarea class="form-control ${(textError??)?string('is-invalid', ' ')}"
                                  value="<#if post??>${post.text}</#if>" name="text" id="message-text"></textarea>
                        <#if textError??>
                            <div class="invalid-feedback">
                                ${textError}
                            </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="tagString" placeholder="Hash tags"/>
                    </div>
                    <div class="form-group">
                        <div class="custom-file">
                            <input type="file" accept="image/*" class="custom-file-input form-control ${(fileError??)?string('is-invalid', ' ')}" id="customFile" name="imageFile"/>
                            <label class="custom-file-label" for="customFile">Choose file</label>
                            <#if fileError??>
                                <div class="invalid-feedback">
                                    ${fileError}
                                </div>
                            </#if>
                        </div>

                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </form>
        </div>
    </div>
</div>