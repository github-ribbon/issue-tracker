/** Issue Tracker Client 1.0.0.Final 
 * 
 * Project created by Andrzej Stążecki 
 * 
 * (Web site: http://andrzejstazecki.blogspot.com)
 * 
 **/

/** Root path for the application **/
var ROOT = "/issuetracker-client/";
var responseList = false;

function redirect(url){
	document.location.href=ROOT+url;
}


/*
 * Document loaded
 */
$(document).ready(function() {	

	/**
	 * Shows ajax loading image during sending form (actions: add, update, delete)
	 */
	function showFormAjaxLoader(){
		$("#form_ajax_loader").show();
	}

	/**
	 *  Hides ajax loading image after sending form (actions: add, update, delete)
	 */
	function hideFormAjaxLoader(){
		$("#form_ajax_loader").hide();
	}

	function removeUserDTOValidationErrors(){
		$("div[field-error-id='user.id.userId']").remove();
		$("div[field-error-id='user.name']").remove();
		$("div[field-error-id='user.surname']").remove();
		$("div[field-error-id='user.password']").remove();
	}

	function removeProjectDTOValidationErrors(){
		$("div[field-error-id='project.id.projectId']").remove();
		$("div[field-error-id='project.description']").remove();
	}

	function removeVersionDTOValidationErrors(){
		$("div[field-error-id='version.id.versionId']").remove();
	}

	function removeIssueDTOValidationErrors(){
		$("div[field-error-id='issue.projectId']").remove();
		$("div[field-error-id='issue.content']").remove();
		$("div[field-error-id='issue.title']").remove();
		$("div[field-error-id='issue.priorityId']").remove();
		$("div[field-error-id='issue.issueTypeId']").remove();
		$("div[field-error-id='issue.assignee']").remove();
	}

	function removeFixedVersionDTOValidationErrors(){
		$("div[field-error-id='fixedVersion.id.versionId']").remove();
	}

	function removeAffectedVersionDTOValidationErrors(){
		$("div[field-error-id='affectedVersion.id.versionId']").remove();
	}

	function removeResponseDTOValidationErrors(){
		$("div[field-error-id='response.content']").remove();
	}

	function disableUserDTOFields(){
		$("input[field-id='user.id.userId']").attr("readonly","readonly");
		$("input[field-id='user.name']").attr("readonly","readonly");
		$("input[field-id='user.surname']").attr("readonly","readonly");
		$("input[field-id='user.password']").attr("readonly","readonly");
	}

	function disableProjectDTOFields(){
		$("input[field-id='project.id.projectId']").attr("readonly","readonly");
		$("textarea[field-id='project.description']").attr("readonly","readonly");
	}

	function disableIssueDTOFields(){		
		$("input[field-id='issue.projectId']").attr("readonly","readonly");
		$("textarea[field-id='issue.content']").attr("readonly","readonly");
		$("input[field-id='issue.title']").attr("readonly","readonly");
		$("input[field-id='issue.priorityId']").attr("readonly","readonly");
		$("input[field-id='issue.issueTypeId']").attr("readonly","readonly");
		$("input[field-id='issue.assignee']").attr("readonly","readonly");
	}

	function disableResponseDTOFields(){
		$("input[field-id='response.content']").attr("readonly","readonly");
	}

	function enableUserDTOFields(){
		$("input[field-id='user.id.userId']").removeAttr("readonly");
		$("input[field-id='user.name']").removeAttr("readonly");
		$("input[field-id='user.surname']").removeAttr("readonly");
		$("input[field-id='user.password']").removeAttr("readonly");
	}

	function enableProjectDTOFields(){
		$("input[field-id='project.id.projectId']").removeAttr("readonly");
		$("textarea[field-id='project.description']").removeAttr("readonly");
	}

	function enableIssueDTOFields(){		
		$("input[field-id='issue.projectId']").removeAttr("readonly");
		$("textarea[field-id='issue.content']").removeAttr("readonly");
		$("input[field-id='issue.title']").removeAttr("readonly");
		$("input[field-id='issue.priorityId']").removeAttr("readonly");
		$("input[field-id='issue.issueTypeId']").removeAttr("readonly");
		$("input[field-id='issue.assignee']").removeAttr("readonly");
	}

	function enableResponseDTOFields(){
		$("input[field-id='response.content']").removeAttr("readonly");
	}


	function hideSuccessAlert(){
		$("#success-alert").hide();
	}

	function hideInfoAlert(){
		$("#info-alert").hide();
	}

	function hideErrorAlert(){
		$("#error-alert").hide();
	}


	/**
	 * Returns true if action is succeeded
	 * 
	 */
	function processResponse(response){

		var errors=response.errors;
		var success=false;		

		if(response.exception!=null){
			alert("Provider error: "+response.exception);
		}else if(errors!=null){				

//			display validation errors

			if(responseList){

				var errorAlert="";

				for(var i=0;i<errors.length;i++){
					errorAlert+=errors[i].message+". ";			
				}

				alert(errorAlert);
			}else{

				for(var i=0;i<errors.length;i++){
					if($.contains(document,$("div[field-error-id='"+errors[i].field+"']")[0])){
						$("div[field-error-id='"+errors[i].field+"']").append("<div>"+errors[i].message+"</div>");
					}else{
						$("*[field-id='"+errors[i].field+"']")
						.after("<div field-error-id='"+errors[i].field+"' class='alert alert-danger'><div>"+errors[i].message+"</div></div>");
					}				
				}
			}

		}else{
			success=true;
		}

		return success;
	}

	/**
	 * Calls ajax request
	 */
	function ajax(type, url, data, beforeSend, complete, success){

		$.ajax({
			type : type,
			url : url,
			data:data,
			beforeSend: beforeSend,
			complete: complete,
			success : success,
			dataType: "json",
//			accepts: "application/json",
//			contentType: "application/json",
			timeout: 10000,
			error : function(xhr, statusText,errorThrown) {

				var status=xhr.status;
				var message="";

				if(statusText=="timeout"){
					message="Timeout error";
				}

				if(status==404){
					message="Page not found";
				}else if(status==405){
					message="Method not allowed";
				}

				var errorString="An error occured."+message+" HTTP status: "+status+" Status text: "+statusText
				+" Error thrown: "+errorThrown;

				alert(errorString);
			}
		});		
	}


	/*
	function pagination(wrapper){


		var totalElements=wrapper.totalElements;
		var currentPage=wrapper.currentPage;
		var totalPages=wrapper.totalPages;		
		var pageSize=wrapper.pageSize;

		if(currentPage<0)
			currentPage=0;

		if(currentPage>=totalPages)
			currentPage=totalPages-1;

		$("#current_page").attr("value",currentPage);

		var content="<ul class='pagination pag-box'>";

		if(totalPages>1){

			if(currentPage==0){
				content+="<li class='disabled'><a>←</a></li>";
			}else{
				content+="<li><a class='pag-link' page-number='"+(currentPage-1)+"'>←</a></li>";
			}

			if(currentPage<=5){

				for(i=0;i<currentPage;i++){
					content+="<li><a class='pag-link' page-number='"+i+"'>"+(i+1)+"</a></li>";
				}

				content+="<li class='disabled'><a>"+(currentPage+1)+"</a></li>";
			}else{
				content+="<li><a class='pag-link' page-number='0'>1</a></li><li class='disabled'><a>...</a></li>";

				for(i=currentPage-4;i<currentPage;i++){
					content+="<li><a class='pag-link' page-number='"+i+"'>"+(i+1)+"</a></li>";
				}

				content+="<li class='disabled'><a>"+(currentPage+1)+"</a></li>";
			}

			if((totalPages-(currentPage+1))<=5){

				for(i=currentPage+1;i<totalPages;i++){
					content+="<li><a  class='pag-link' page-number='"+i+"'>"+(i+1)+"</a></li>";
				}

			}else{

				for(i=currentPage+1;i<=currentPage+4;i++){
					content+="<li><a class='pag-link' page-number='"+i+"'>"+(i+1)+"</a></li>";
				}

				content+="<li class='disabled'><a>...</a></li><li><a class='pag-link' page-number='"+(totalPages-1)+"'>"+totalPages+"</a></li>";
			}

			if((currentPage+1)==totalPages){
				content+="<li class='disabled'><a>→</a></li>";
			}else{
				content+="<li><a  class='pag-link' page-number='"+(currentPage+1)+"'>→</a></li>";
			}
		}

		content+="</ul>";	


		var pageSelectorContent="<div class='col-md-2 text-center padding-small'>" +
				"<select class='selectpicker show-tick' id='project_list_page_size_selector'>";


		var selected="";

		for(i=1;i<=totalElements/2;i++){


			if(i==pageSize)
				selected="selected='selected'";else
				selected="";

			pageSelectorContent+="<option value='"+i+"' "+selected+">"+i+"</option>";
		}

		if(i==pageSize)
			selected="selected='selected'";
		else
			selected="";

		pageSelectorContent+="<option value='"+totalElements+"' "+selected+">All records</option>";
		pageSelectorContent+="</select></div>";


		$("#pagination").html(content);
		$("#page_size_container").html(pageSelectorContent);
	}
	 */

	/**
	 * Protection against XSS.  
	 */
	function text(unsafeText){
		if(unsafeText==null || unsafeText=="")
			return "";
		else
			return $ESAPI.encoder().encodeForHTML(unsafeText);
	}



//	end of functions


//	initializing

	org.owasp.esapi.ESAPI.initialize();


	$('.selectpicker').selectpicker();

	$('.bootstrap-select').hover(function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(100).fadeIn();
	}, function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(100).fadeOut();
	});

	hideFormAjaxLoader();

	hideSuccessAlert();
	hideInfoAlert();
	hideErrorAlert();


//	auto drop down

	$('.dropdown-toggle').dropdown();

	$('.dropdown').hover(function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(100).fadeIn();
	}, function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(100).fadeOut();
	});

//	events



	$(document).on("change","#project_list_order_selector",function(){
		$("#current_page").attr("value","0");
		$("#project_list_form").submit();
	});

	$(document).on("change","#project_list_page_size_selector",function(){
		$("#current_page").attr("value","0");
		$("#project_list_form").submit();

	});

	$(document).on("change","#project_list_owner_selector",function(){	
		$("#current_page").attr("value","0");
		$("#project_list_form").submit();
	});



	$(document).on("change","#issue_list_order_selector",function(){
		$("#current_page").attr("value","0");
		$("#issue_list_form").submit();
	});

	$(document).on("change","#issue_list_page_size_selector",function(){
		$("#current_page").attr("value","0");
		$("#issue_list_form").submit();

	});

	$(document).on("change","#issue_list_reporter_selector," +
			"#issue_list_assignee_selector," +
			"#issue_list_status_selector," +
			"#issue_list_priority_selector," +
			"#issue_list_type_selector",function(){	
		$("#current_page").attr("value","0");
		$("#issue_list_form").submit();
	});



	$("#register_user_button").on("click",function(){
		ajax("POST", ROOT+"registration",{
			"user.id.userId":$("input[field-id='user.id.userId']").val(),
			"user.name":$("input[field-id='user.name']").val(),
			"user.surname":$("input[field-id='user.surname']").val(),
			"user.password":$("input[field-id='user.password']").val()
		}, function(){
			removeUserDTOValidationErrors();
			disableUserDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableUserDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){
				redirect("login?reg");
			}
		});
	});


	$("#save_user_button").on("click",function(){
		ajax("POST", ROOT+"account",{
			"user.id.userId":null,
			"user.name":$("input[field-id='user.name']").val(),
			"user.surname":$("input[field-id='user.surname']").val(),
			"user.password":$("input[field-id='user.password']").val()
		}, function(){
			removeUserDTOValidationErrors();
			disableUserDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableUserDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("login?account_updated");
			}
		});
	});



	$("#add_project_button").on("click",function(){
		ajax("POST", ROOT+"projects/add",{			
			"project.id.projectId":$("input[field-id='project.id.projectId']").val(),
			"project.description":$("textarea[field-id='project.description']").val()			
		}, function(){
			removeProjectDTOValidationErrors();
			disableProjectDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableProjectDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("projects/view?owner_id="
						+response.project.id.ownerId
						+"&project_id="+response.project.id.projectId+"&created");
			}
		});
	});


	$("#save_project_button").on("click",function(){
		ajax("POST", ROOT+"projects/save",{			
			"project.id.projectId":$("input[field-id='project.id.projectId']").val(),
			"project.description":$("textarea[field-id='project.description']").val()			
		}, function(){
			removeProjectDTOValidationErrors();
			disableProjectDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableProjectDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){

				redirect("projects/view?owner_id="
						+response.project.id.ownerId
						+"&project_id="+response.project.id.projectId+"&updated");
			}
		});
	});

	$("#delete_project_button").on("click",function(){
		ajax("POST", ROOT+"projects/delete",{			
			"project.id.projectId":$("input[field-id='project.id.projectId']").val(),
			"project.description":$("textarea[field-id='project.description']").val()			
		}, function(){
			removeProjectDTOValidationErrors();
			disableProjectDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableProjectDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){

				redirect("projects/view?owner_id="
						+response.project.id.ownerId
						+"&project_id="+response.project.id.projectId+"&updated");
			}
		});
	});


	$("#add_version_button").on("click",function(){
		ajax("POST", ROOT+"versions/add",{			
			"version.id.projectId":$("input[field-id='version.id.projectId']").val(),
			"version.id.versionId":$("input[field-id='version.id.versionId']").val()			
		}, function(){
			removeVersionDTOValidationErrors();
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("projects/view?owner_id="
						+response.version.id.ownerId
						+"&project_id="+response.version.id.projectId+"&version_created");
			}
		});
	});

	
	$(".delete_version_form").on("submit",function(e){

		e.preventDefault();

		ajax("POST", ROOT+"versions/delete",{			
			"version.id.projectId":$(this).find("input[list-field-id='version.id.projectId']").val(),
			"version.id.versionId":$(this).find("input[list-field-id='version.id.versionId']").val()
		}, function(){
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){
				redirect("projects/view?owner_id="
						+response.version.id.ownerId
						+"&project_id="+response.version.id.projectId+"&version_deleted");			
			}
		});
	});



	$("#add_issue_button").on("click",function(){
		ajax("POST", ROOT+"issues/add",{	
			"issue.statusId":$("input[field-id='issue.statusId']").val(),
			"issue.ownerId":$("input[field-id='issue.ownerId']").val(),
			"issue.projectId":$("input[field-id='issue.projectId']").val(),
			"issue.content":$("textarea[field-id='issue.content']").val(),
			"issue.title":$("input[field-id='issue.title']").val(),
			"issue.priorityId":$("*[field-id='issue.priorityId']").val(),
			"issue.issueTypeId":$("*[field-id='issue.issueTypeId']").val(),
			"issue.assignee":$("*[field-id='issue.assignee']").val(),
		}, function(){
			removeIssueDTOValidationErrors();
			disableIssueDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableIssueDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("issues/view?issue_id="
						+response.issue.issueId+"&created");
			}
		});
	});

	$("#save_issue_button").on("click",function(){

		ajax("POST", ROOT+"issues/save",{
			"issue.issueId":$("input[field-id='issue.issueId']").val(),
			"issue.content":$("textarea[field-id='issue.content']").val(),
			"issue.title":$("input[field-id='issue.title']").val(),
			"issue.priorityId":$("*[field-id='issue.priorityId']").val(),
			"issue.statusId":$("*[field-id='issue.statusId']").val(),
			"issue.issueTypeId":$("*[field-id='issue.issueTypeId']").val(),
			"issue.assignee":$("*[field-id='issue.assignee']").val(),
		}, function(){
			removeIssueDTOValidationErrors();
			disableIssueDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableIssueDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("issues/view?issue_id="
						+response.issue.issueId+"&updated");
			}
		});
	});


	$("#delete_issue_button").on("click",function(){
		ajax("POST", ROOT+"issues/delete",{	
			"issue.issueId":$("input[field-id='issue.issueId']").val()
		}, function(){
			removeIssueDTOValidationErrors();
			disableIssueDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableIssueDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("issues/view?issue_id="
						+response.issue.issueId+"&deleted");
			}
		});
	});


	$(document).on("change","#fixed_version_selector",function(){

		ajax("POST", ROOT+"fixed-versions/add",{
			"fixedVersion.id.issueId":$("input[field-id='fixedVersion.id.issueId']").val(),
			"fixedVersion.id.ownerId":$("input[field-id='fixedVersion.id.ownerId']").val(),
			"fixedVersion.id.projectId":$("input[field-id='fixedVersion.id.projectId']").val(),
			"fixedVersion.id.versionId":$("*[field-id='fixedVersion.id.versionId']").val()
		}, function(){
			removeFixedVersionDTOValidationErrors();
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("issues/view?issue_id="
						+response.fixedVersion.id.issueId+"&fixed_version_created");
			}
		});


	});



	$(".delete_fixed_version_form").on("submit",function(e){

		e.preventDefault();

		ajax("POST", ROOT+"fixed-versions/delete",{			
			"fixedVersion.id.issueId":$(this).find("input[list-field-id='fixedVersion.id.issueId']").val(),
			"fixedVersion.id.ownerId":$(this).find("input[list-field-id='fixedVersion.id.ownerId']").val(),
			"fixedVersion.id.projectId":$(this).find("input[list-field-id='fixedVersion.id.projectId']").val(),
			"fixedVersion.id.versionId":$(this).find("input[list-field-id='fixedVersion.id.versionId']").val()
		}, function(){
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){
				redirect("issues/view?issue_id="
						+response.fixedVersion.id.issueId+"&fixed_version_deleted");			
			}
		});
	});



	$(document).on("change","#affected_version_selector",function(){

		ajax("POST", ROOT+"affected-versions/add",{
			"affectedVersion.id.issueId":$("input[field-id='affectedVersion.id.issueId']").val(),
			"affectedVersion.id.ownerId":$("input[field-id='affectedVersion.id.ownerId']").val(),
			"affectedVersion.id.projectId":$("input[field-id='affectedVersion.id.projectId']").val(),
			"affectedVersion.id.versionId":$("*[field-id='affectedVersion.id.versionId']").val()
		}, function(){
			removeAffectedVersionDTOValidationErrors();
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("issues/view?issue_id="
						+response.affectedVersion.id.issueId+"&affected_version_created");
			}
		});


	});



	$(".delete_affected_version_form").on("submit",function(e){

		e.preventDefault();

		ajax("POST", ROOT+"affected-versions/delete",{			
			"affectedVersion.id.issueId":$(this).find("input[list-field-id='affectedVersion.id.issueId']").val(),
			"affectedVersion.id.ownerId":$(this).find("input[list-field-id='affectedVersion.id.ownerId']").val(),
			"affectedVersion.id.projectId":$(this).find("input[list-field-id='affectedVersion.id.projectId']").val(),
			"affectedVersion.id.versionId":$(this).find("input[list-field-id='affectedVersion.id.versionId']").val()
		}, function(){
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){
				redirect("issues/view?issue_id="
						+response.affectedVersion.id.issueId+"&affected_version_deleted");			
			}
		});
	});



	$("#add_response_button").on("click",function(){
		ajax("POST", ROOT+"responses/add",{			
			"response.content":$("textarea[field-id='response.content']").val(),
			"response.issueId":$("input[field-id='response.issueId']").val()			
		}, function(){
			removeResponseDTOValidationErrors();
			disableResponseDTOFields();
			showFormAjaxLoader();
		}, function(){
			enableResponseDTOFields();
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){				
				redirect("issues/view?issue_id="
						+response.response.issueId+"&response_created");		
			}
		});
	});




	$(".delete_response_form .delete_response_button").on("click",function(){

		ajax("POST", ROOT+"responses/delete",{			
			"response.responseId":$(this).parent().parent().find("input[list-field-id='response.responseId']").val()
		}, function(){
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			if(processResponse(response)){
				redirect("issues/view?issue_id="
						+$("input[field-id='issue.issueId']").val()	+"&response_deleted");			
			}
		});
	});


	$(".save_response_form .save_response_button").on("click",function(){

		ajax("POST", ROOT+"responses/save",{			
			"response.responseId":$(this).parent().parent().find("input[list-field-id='response.responseId']").val(),
			"response.content":$(this).parent().parent().find("textarea[list-field-id='response.content']").val()
		}, function(){
			showFormAjaxLoader();
		}, function(){
			hideFormAjaxLoader();
		}, function(response){
			responseList=true;
			if(processResponse(response)){
				redirect("issues/view?issue_id="
						+$("input[field-id='issue.issueId']").val()	+"&response_updated");			
			}
		});
	});

});