import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AnneeUniversitaire } from './annee-universitaire.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AnneeUniversitaireService {

    private resourceUrl = 'api/annee-universitaires';
    private resourceSearchUrl = 'api/_search/annee-universitaires';

    constructor(private http: Http) { }

    create(anneeUniversitaire: AnneeUniversitaire): Observable<AnneeUniversitaire> {
        const copy = this.convert(anneeUniversitaire);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(anneeUniversitaire: AnneeUniversitaire): Observable<AnneeUniversitaire> {
        const copy = this.convert(anneeUniversitaire);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AnneeUniversitaire> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(anneeUniversitaire: AnneeUniversitaire): AnneeUniversitaire {
        const copy: AnneeUniversitaire = Object.assign({}, anneeUniversitaire);
        return copy;
    }
}
